/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Bogdan Rechi
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package bogdanrechi.xml.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bogdanrechi.commons.Platform;
import bogdanrechi.commons.Property;
import bogdanrechi.commons.Run;
import bogdanrechi.commons.exceptions.StringException;
import bogdanrechi.commons.file.FileContent;
import bogdanrechi.commons.file.Files;
import bogdanrechi.commons.math.Maths;
import bogdanrechi.commons.time.TimeMeasure;
import bogdanrechi.commons.xml.Xml;
import bogdanrechi.swt.commons.SwtFiles;
import bogdanrechi.swt.commons.SwtMessageBox;
import bogdanrechi.swt.commons.SwtPlatform;
import bogdanrechi.swt.commons.SwtPoints;
import bogdanrechi.swt.commons.SwtResourceManager;
import bogdanrechi.swt.commons.SwtShell;
import bogdanrechi.swt.commons.SwtText;
import bogdanrechi.swt.commons.dialogs.AboutBox;
import bogdanrechi.swt.commons.textbox.EnhancedStyledText;
import bogdanrechi.swt.commons.textbox.IndentingImpl;
import bogdanrechi.swt.commons.textbox.UndoRedoImpl;
import bogdanrechi.xml.operations.resources.XmlOperationsResources;

/**
 * XML operations
 *
 * @author Bogdan Rechi
 */
public class MainWindow
{
  private static final String BOM_WARNING2 = "BOM warning: the %s file you have selected has a Byte Order Mark (BOM) as prolog. "
      + "This will not be supported by the XML libraries in the system so do you want to work on a no-BOM copy of the file?";

  private static final String BOM_WARNING = "BOM warning: the file you have selected has a Byte Order Mark (BOM) as prolog. "
      + "This will not be supported by the XML libraries in the system so do you want to work on a no-BOM copy of the file?";

  // Constants
  private static final String XML_OPERATIONS_TITLE = "XML Operations - %s%s";

  private static final int TEXT_TAB_SIZE = 2;

  private static final String DTD_TEMP_FILE = Files.appendToPath(Platform.TEMPORARY_DIRECTORY, "xmloperationsdtd.dtd");

  private static final String XSLT_NO_BLANKS = "<xsl:strip-space elements=\"*\"/>";
  private static final int XSLT_NO_BLANKS_LENGTH = XSLT_NO_BLANKS.length();

  private static final String XQUERY_SOURCE_FILE_PATH = "declare variable $sourceFilePath as xs:string external;";
  private static final int XQUERY_SOURCE_FILE_PATH_LENGTH = XQUERY_SOURCE_FILE_PATH.length();

  private static Color COLOR_RED;
  private static Color COLOR_BLACK;
  private static Color COLOR_GREEN;

  private static int LABEL_GAP = 6;

  // Fields
  protected Shell _shlXmlOperations;

  // region

  /**
   * Type of current file
   */
  enum FileType
  {
   XML,
   XPATH,
   XSLT,
   XQUERY,
   XSD,
   DTD
  }

  /**
   * Last pressed toolbar button
   */
  enum LastPressedButton
  {
   NEW,
   OPEN,
   SAVE,
   SAVE_AS
  }

  // region

  FileType _lastFocusedFileType = FileType.XML;
  FileType _lastFocusedOperationType = FileType.XPATH;

  UndoRedoImpl _xmlFileUndoRedo;
  UndoRedoImpl _xpathFileUndoRedo;
  UndoRedoImpl _xsltFileUndoRedo;
  UndoRedoImpl _xqueryFileUndoRedo;
  UndoRedoImpl _xsdFileUndoRedo;
  UndoRedoImpl _dtdFileUndoRedo;

  IndentingImpl _xmlFileIndenting;
  IndentingImpl _xsltFileIndenting;
  IndentingImpl _xqueryFileIndenting;
  IndentingImpl _xsdFileIndenting;
  IndentingImpl _dtdFileIndenting;

  String _xmlFile = "";
  String _xpathFile = "";
  String _xsltFile = "";
  String _xqueryFile = "";
  String _xsdFile = "";
  String _dtdFile = "";

  boolean _xmlFileChanged = false;
  boolean _xpathFileChanged = false;
  boolean _xsltFileChanged = false;
  boolean _xqueryFileChanged = false;
  boolean _xsdFileChanged = false;
  boolean _dtdFileChanged = false;

  List<String> _extensions;

  // region

  boolean _modifyingFile = false;

  LastPressedButton _lastPressedButton = LastPressedButton.NEW;

  EnhancedStyledText _lastSelectedForClipboard = null;
  UndoRedoImpl _lastSelectedForClipboardUndoRedo = null;

  boolean _firstTime = true;

  int _searchBoxFocusTime = 0;

  String _xpathTags = null;
  String _xsltTags = null;
  String _xqueryTags = null;

  private FileTransfer _fileTransfer;

  private Program _defaultBrowserProgram;

  private String _resultFile = Files.appendToPath(Platform.TEMPORARY_DIRECTORY, "xmloperations.html");

  private Cursor _cursorWait;

  private Gson _gson;
  private Configuration _config;

  public static Logger _log;

  private Display _display;

  private Color _editBackground;

  private SwtResourceManager _swtResourceManager;

  // region controls

  private EnhancedStyledText textXml;
  private EnhancedStyledText textXpath;
  private EnhancedStyledText textXslt;
  private EnhancedStyledText textXsd;
  private EnhancedStyledText textDtd;
  private EnhancedStyledText textResult;
  private EnhancedStyledText textSearch;
  private Button btnSave;
  private Composite compositeXmlOthers;
  private TabItem tabXpath;
  private TabItem tabXslt;
  private TabItem tabXsd;
  private TabItem tabDtd;
  private TabFolder tabsOthers;
  private Button btnPaste;
  private Button btnCopy;
  private Button btnCut;
  private Button btnUndo;
  private Button btnRedo;
  private Button btnFormat;
  private Button btnNew;
  private Button btnOpen;
  private Button btnSaveAs;
  private Button btnAutoSave;
  private TabItem tabXml;
  private TabFolder tabsXml;
  private Button btnCopyFolder;
  private Composite compositeStatusBar;
  private Label lblStatus;
  private Composite compositeMain;
  private Composite compositeToolBar;
  private Button btnFont;
  private Button btnClearResult;
  private Button btnNamespaces;
  private Button btnAbout;
  private SashForm splitSourceResult;
  private SashForm splitXmlOthers;
  private Composite compositeResult;
  private TabFolder tabsResultText;
  private TabItem tabResultText;
  private TabItem tabXquery;
  private EnhancedStyledText textXquery;
  private ToolBar toolBarApply;
  private ToolItem btnDropDownApply;
  private Menu menuApply;
  private MenuItem menuItemApplyAndShowInBrowser;
  private MenuItem menuItemShowInBrowser;
  private DropTarget dropTarget;

  // region methods

  public static void main(String[] args)
  {
    Platform.validateCurrentJavaVersion("1.9");
    SwtPlatform.validateCurrentSwtVersion(4763);

    try
    {
      MainWindow window = new MainWindow();
      window.open();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Open the window
   */
  public void open() throws IOException
  {
    _log = LogManager.getLogger(MainWindow.class);

    _extensions = Arrays.asList(
        "xml", "xml.nobom", "config", "config.nobom", "csproj", "csproj.nobom",
        "xpath", "xpath.nobom",
        "xsl", "xsl.nobom", "xslt", "xslt.nobom",
        "xsd", "xsd.nobom",
        "xquery", "xquery.nobom", "xq", "xq.nobom", "xqy", "xqy.nobom",
        "dtd", "dtd.nobom");

    try
    {
      _display = Display.getDefault();

      _swtResourceManager = SwtResourceManager.getDisplayManager();

      COLOR_RED = _swtResourceManager.getColor(SWT.COLOR_RED);
      COLOR_BLACK = _swtResourceManager.getColor(SWT.COLOR_BLACK);
      COLOR_GREEN = _swtResourceManager.getColor(SWT.COLOR_DARK_GREEN);

      _cursorWait = _swtResourceManager.getCursor(SWT.CURSOR_WAIT);

      _editBackground = _swtResourceManager.getColor(253, 253, 253);

      createContents();

      _xmlFileUndoRedo = new UndoRedoImpl(textXml);
      _xpathFileUndoRedo = new UndoRedoImpl(textXpath);
      _xsltFileUndoRedo = new UndoRedoImpl(textXslt);
      _xqueryFileUndoRedo = new UndoRedoImpl(textXquery);
      _xsdFileUndoRedo = new UndoRedoImpl(textXsd);
      _dtdFileUndoRedo = new UndoRedoImpl(textDtd);

      _xmlFileIndenting = new IndentingImpl(textXml);
      _xsltFileIndenting = new IndentingImpl(textXslt);
      _xqueryFileIndenting = new IndentingImpl(textXquery);
      _xsdFileIndenting = new IndentingImpl(textXsd);
      _dtdFileIndenting = new IndentingImpl(textDtd);

      menuApply = new Menu(_shlXmlOperations);
      _shlXmlOperations.setMenu(menuApply);

      menuItemApplyAndShowInBrowser = new MenuItem(menuApply, SWT.NONE);
      menuItemApplyAndShowInBrowser.addSelectionListener(new SelectionAdapter()
      {
        @Override
        public void widgetSelected(SelectionEvent e)
        {
          onKeyEvent(null, SWT.F5, e.stateMask | SWT.CONTROL);
          setFocus(_lastFocusedFileType);
        }
      });
      menuItemApplyAndShowInBrowser.setEnabled(false);
      menuItemApplyAndShowInBrowser.setText("Apply and Show Result in Browser (Ctrl+F5)");

      menuItemShowInBrowser = new MenuItem(menuApply, SWT.NONE);
      menuItemShowInBrowser.addSelectionListener(new SelectionAdapter()
      {
        @Override
        public void widgetSelected(SelectionEvent e)
        {
          onKeyEvent(null, SWT.F6, e.stateMask);
          setFocus(_lastFocusedFileType);
        }
      });
      menuItemShowInBrowser.setEnabled(false);
      menuItemShowInBrowser.setText("Show Result in Browser (F6)");

      GsonBuilder builder = new GsonBuilder();
      _gson = builder
          .serializeNulls()
          .setPrettyPrinting()
          .create();

      _config = _gson.fromJson(FileContent.readTextFile(Platform.CURRENT_DIRECTORY + "xmloperations.json"), Configuration.class);

      setFont(_config.fontName, _config.fontSize, _config.fontStyle);

      textXml.setWordWrap(_config.xmlWrap);
      textXpath.setWordWrap(_config.xpathWrap);
      textXslt.setWordWrap(_config.xsltWrap);
      textXquery.setWordWrap(_config.xqueryWrap);
      textXsd.setWordWrap(_config.xsdWrap);
      textDtd.setWordWrap(_config.dtdWrap);
      textResult.setWordWrap(_config.resultWrap);

      dropTarget = new DropTarget(_shlXmlOperations, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
      dropTarget.setTransfer(new Transfer[] { _fileTransfer = FileTransfer.getInstance() });
      dropTarget.addDropListener(new DropTargetAdapter()
      {
        @Override
        public void dragEnter(DropTargetEvent event)
        {
          event.detail = DND.DROP_DEFAULT;

          if (Platform.SYSTEM_IS_LINUX)
          {
            return;
          }

          if (event.dataTypes != null)
          {
            if (event.dataTypes.length > 0)
            {
              String[] files = (String[]) _fileTransfer.nativeToJava(event.dataTypes[0]);
              for (String file : files)
              {
                String fileName = file.toLowerCase();

                boolean ok = false;

                for (String extension : _extensions)
                {
                  if (fileName.endsWith(extension))
                  {
                    ok = true;
                    break;
                  }
                }

                if (!ok)
                {
                  event.detail = DND.DROP_NONE;
                  return;
                }
              }

              return;
            }
          }

          event.detail = DND.DROP_NONE;
        }

        @Override
        public void drop(final DropTargetEvent event)
        {
          _shlXmlOperations.forceActive();
          _shlXmlOperations.forceFocus();

          _display.asyncExec(new Runnable()
          {
            @Override
            public void run()
            {
              if (event.data != null)
              {
                for (String file : (String[]) event.data)
                {
                  loadDroppedFile(file);
                }
              }
            }
          });
        }
      });

      _xpathTags = FileContent.readTextFileFromResources(XmlOperationsResources.XPATH_TAGS_TXT);
      _xsltTags = FileContent.readTextFileFromResources(XmlOperationsResources.XSLT_TAGS_TXT);
      _xqueryTags = FileContent.readTextFileFromResources(XmlOperationsResources.XQUERY_TAGS_TXT);

      _xsltTags = String.format("<a>\n%s%s\n</a>", _xpathTags, _xsltTags);
      _xqueryTags = String.format("<a>\n%s%s\n</a>", _xpathTags, _xqueryTags);
      _xpathTags = String.format("<a>\n%s</a>", _xpathTags);

      _shlXmlOperations.open();
      _shlXmlOperations.layout();

      while (!_shlXmlOperations.isDisposed())
      {
        if (!_display.readAndDispatch())
        {
          _display.sleep();
        }
      }
    }
    catch (Throwable e)
    {
      _log.fatal(Run.getExceptionStackTrace(e));
      SwtMessageBox.messageBoxError("The application is closing due to a severe internal error.\nSee the log file for details");
    }
    finally
    {
      SwtResourceManager.disposeAll();
    }
  }

  /**
   * Create contents of the window
   */
  protected void createContents()
  {
    _shlXmlOperations = new Shell();
    _shlXmlOperations.addDisposeListener(new DisposeListener()
    {
      @Override
      public void widgetDisposed(DisposeEvent e)
      {
        SwtResourceManager.disposeAll();
      }
    });
    _shlXmlOperations.addShellListener(new ShellAdapter()
    {
      @Override
      public void shellClosed(ShellEvent e)
      {
        _shlXmlOperations.setActive();

        if (!closingXmlFile(e))
        {
          return;
        }

        if (!closingXpathFile(e))
        {
          return;
        }

        if (!closingXsltFile(e))
        {
          return;
        }

        if (!closingXqueryFile(e))
        {
          return;
        }

        if (!closingXsdFile(e))
        {
          return;
        }

        if (!closingDtdFile(e))
        {
          return;
        }

        saveConfiguration();

        Files.delete(DTD_TEMP_FILE);

        e.doit = true;
      }

      @Override
      public void shellActivated(ShellEvent e)
      {
        if (_firstTime)
        {
          _defaultBrowserProgram = Program.findProgram("html");
          if (_defaultBrowserProgram == null)
          {
            _log.warn("Unable to detect the default system browser");
          }

          textXml.setFocus();
          _firstTime = false;
        }
      }
    });
    _shlXmlOperations.setMinimumSize(new Point(970, 500));
    _shlXmlOperations.setSize(971, 600);
    _shlXmlOperations.setText("XML Operations");
    _shlXmlOperations.setLayout(new FillLayout(SWT.HORIZONTAL));
    _shlXmlOperations.setImages(new Image[] {
        _swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "mainxml.png"),
        _swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "mainbigxml.png"),
        _swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "maininterxml.png")
    });

    SwtShell.CurrentApplicationShell = _shlXmlOperations;

    compositeMain = new Composite(_shlXmlOperations, SWT.NONE);
    compositeMain.setLayout(new GridLayout(1, false));

    compositeToolBar = new Composite(compositeMain, SWT.NONE);
    compositeToolBar.setLayout(new RowLayout(SWT.HORIZONTAL));
    compositeToolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

    btnNew = new Button(compositeToolBar, SWT.NONE);
    btnNew.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "new.png"));
    btnNew.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastPressedButton = LastPressedButton.NEW;
        fileNew(_lastFocusedFileType);
      }
    });
    btnNew.setToolTipText("New XML (Ctrl+N)");

    btnOpen = new Button(compositeToolBar, SWT.NONE);
    btnOpen.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "open.png"));
    btnOpen.setToolTipText("Open XML (Ctrl+O)");
    btnOpen.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastPressedButton = LastPressedButton.OPEN;
        fileOpen(_lastFocusedFileType);
      }
    });
    btnOpen.setText("Open");

    btnSave = new Button(compositeToolBar, SWT.NONE);
    btnSave.setEnabled(false);
    btnSave.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "save.png"));
    btnSave.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastPressedButton = LastPressedButton.SAVE;
        fileSave(_lastFocusedFileType);
      }
    });
    btnSave.setToolTipText("Save XML (Ctrl+S)");
    btnSave.setText("Save");

    btnSaveAs = new Button(compositeToolBar, SWT.NONE);
    btnSaveAs.setText("As");
    btnSaveAs.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastPressedButton = LastPressedButton.SAVE_AS;
        fileSaveAs(_lastFocusedFileType);
      }
    });
    btnSaveAs.setToolTipText("Save XML as");

    Label labelSeparator4 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator4.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    textSearch = new EnhancedStyledText(compositeToolBar, SWT.BORDER | SWT.SINGLE, "en");
    textSearch.setBackground(_editBackground);
    textSearch.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        if (e.time == _searchBoxFocusTime)
        {
          textSearch.selectAll();
        }
      }
    });
    textSearch.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _searchBoxFocusTime = e.time;
        textSearch.selectAll();
      }
    });
    textSearch.setLayoutData(new RowData(160, SWT.DEFAULT));
    textSearch.setToolTipText("Search text (Ctrl+F, F3)\nHold Ctrl for case-sensitive search");
    textSearch.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        switch (e.keyCode)
        {
          case SWT.CR:
          case SWT.F3:
            search((e.stateMask & SWT.CONTROL) != 0);
            if ((e.stateMask & SWT.CONTROL) != 0)
            {
              e.doit = false;
            }
            break;

          case SWT.ARROW_LEFT:
          case SWT.ARROW_RIGHT:
            break;

          case SWT.ESC:
            if (_lastSelectedForClipboard != null)
            {
              _lastSelectedForClipboard.setFocus();
            }
            e.doit = false;
            break;

          default:
            onKeyEvent(null, e.keyCode, e.stateMask);

            if ((e.stateMask & SWT.CONTROL) != 0)
            {
              e.doit = false;
            }
            break;
        }
      }
    });

    Label labelSeparator1 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator1.setLayoutData(new RowData(LABEL_GAP, -1));

    btnCopyFolder = new Button(compositeToolBar, SWT.NONE);
    btnCopyFolder.setToolTipText("Copy XML file's folder path");
    btnCopyFolder.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        String copiedFolder = "";

        switch (_lastFocusedFileType)
        {
          case DTD:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_dtdFile));
            textDtd.setFocus();
            break;

          case XML:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_xmlFile));
            textXml.setFocus();
            break;

          case XPATH:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_xpathFile));
            textXpath.setFocus();
            break;

          case XSD:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_xsdFile));
            textXsd.setFocus();
            break;

          case XSLT:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_xsltFile));
            textXslt.setFocus();
            break;

          case XQUERY:
            SwtText.copyTextToClipboard(copiedFolder = Files.getParent(_xqueryFile));
            textXquery.setFocus();
            break;
        }

        SwtMessageBox.messageBox("Copied to clipboard:\n\n" + copiedFolder, "XML Operations");
      }
    });
    btnCopyFolder.setEnabled(false);
    btnCopyFolder.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "copyfolder.png"));

    btnCut = new Button(compositeToolBar, SWT.NONE);
    btnCut.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastSelectedForClipboard.cut();
        _lastSelectedForClipboard.setFocus();
      }
    });
    btnCut.setToolTipText("Cut (Ctrl+X)");
    btnCut.setEnabled(false);
    btnCut.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "cut.png"));

    btnCopy = new Button(compositeToolBar, SWT.NONE);
    btnCopy.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        _lastSelectedForClipboard.copy();
        _lastSelectedForClipboard.setFocus();
      }
    });
    btnCopy.setToolTipText("Copy (Ctrl+Ins)");
    btnCopy.setEnabled(false);
    btnCopy.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "copy.png"));

    btnPaste = new Button(compositeToolBar, SWT.NONE);
    btnPaste.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        if ((e.stateMask & SWT.CTRL) != 0 && _lastFocusedOperationType == FileType.XSLT)
        {
          int selectionPos = textXslt.getSelection().x + XSLT_NO_BLANKS_LENGTH;
          textXslt.insert(XSLT_NO_BLANKS);
          textXslt.setSelection(selectionPos, selectionPos);
          textXslt.setFocus();
        }
        else
          if ((e.stateMask & SWT.CTRL) != 0 && _lastFocusedOperationType == FileType.XQUERY)
          {
            int selectionPos = textXquery.getSelection().x + XQUERY_SOURCE_FILE_PATH_LENGTH;
            textXquery.insert(XQUERY_SOURCE_FILE_PATH);
            textXquery.setSelection(selectionPos, selectionPos);
            textXquery.setFocus();
          }
          else
          {
            _lastSelectedForClipboard.paste();
            _lastSelectedForClipboard.setFocus();
          }
      }
    });
    btnPaste.setToolTipText("Paste (Shift+Ins)");
    btnPaste.setEnabled(false);
    btnPaste.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "paste.png"));

    Label labelSeparator2 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator2.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnUndo = new Button(compositeToolBar, SWT.NONE);
    btnUndo.setToolTipText("Undo (Ctrl+Z)");
    btnUndo.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        if (_lastSelectedForClipboardUndoRedo != null)
        {
          _lastSelectedForClipboardUndoRedo.undo();
          _lastSelectedForClipboard.setFocus();
        }
      }
    });
    btnUndo.setEnabled(false);
    btnUndo.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "undo.png"));

    btnRedo = new Button(compositeToolBar, SWT.NONE);
    btnRedo.setToolTipText("Redo (Ctrl+Y)");
    btnRedo.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        if (_lastSelectedForClipboardUndoRedo != null)
        {
          _lastSelectedForClipboardUndoRedo.redo();
          _lastSelectedForClipboard.setFocus();
        }
      }
    });
    btnRedo.setEnabled(false);
    btnRedo.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "redo.png"));

    Label labelSeparator7 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator7.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnFormat = new Button(compositeToolBar, SWT.NONE);
    btnFormat.setToolTipText("Format (Alt+F8)");
    btnFormat.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        formatXml();
      }
    });
    btnFormat.setEnabled(true);
    btnFormat.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "format.png"));

    Label labelSeparator8 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator8.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnFont = new Button(compositeToolBar, SWT.NONE);
    btnFont.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "font.png"));
    btnFont.setToolTipText("Font");
    btnFont.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        FontDialog fontDialog = new FontDialog(_shlXmlOperations);
        fontDialog.setFontList(textXml.getFont().getFontData());
        FontData fontData = fontDialog.open();
        if (fontData != null)
        {
          setFont(fontData.getName(), (int) fontData.height, fontData.getStyle());
        }

        setFocus(_lastFocusedFileType);
      }
    });

    btnClearResult = new Button(compositeToolBar, SWT.NONE);
    btnClearResult.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        resetResultText();

        setFocus(_lastFocusedFileType);
      }
    });
    btnClearResult.setToolTipText("Clear result");
    btnClearResult.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "clear.png"));

    Label labelSeparator3 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator3.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnNamespaces = new Button(compositeToolBar, SWT.NONE);
    btnNamespaces.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "namespaces.png"));
    btnNamespaces.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        printNamespaces();
      }
    });
    btnNamespaces.setToolTipText("Namespaces (F4)");
    btnNamespaces.setText("NS:");

    Label labelSeparator81 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator81.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnAutoSave = new Button(compositeToolBar, SWT.TOGGLE);
    btnAutoSave.setToolTipText("Autosave before each operation");
    btnAutoSave.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "autosave.png"));

    toolBarApply = new ToolBar(compositeToolBar, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

    btnDropDownApply = new ToolItem(toolBarApply, SWT.DROP_DOWN);
    btnDropDownApply.setWidth(-1);
    btnDropDownApply.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        if (e.detail == SWT.ARROW)
        {
          Rectangle rect = btnDropDownApply.getBounds();
          Point pt = btnDropDownApply.getParent().toDisplay(new Point(rect.x, rect.y));
          menuApply.setLocation(pt.x, pt.y + rect.height);
          menuApply.setVisible(true);
        }
        else
        {
          onKeyEvent(null, SWT.F5, e.stateMask);
          setFocus(_lastFocusedFileType);
        }
      }
    });
    btnDropDownApply.setText("Apply");
    btnDropDownApply.setToolTipText("Apply XPath (F5)");
    btnDropDownApply.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "apply.png"));

    Label labelSeparator5 = new Label(compositeToolBar, SWT.NONE);
    labelSeparator5.setLayoutData(new RowData(LABEL_GAP, SWT.DEFAULT));

    btnAbout = new Button(compositeToolBar, SWT.NONE);
    btnAbout.setImage(_swtResourceManager.loadResourceImage(MainWindow.class, XmlOperationsResources.RESOURCES_ICONS_PATH + "about.png"));
    btnAbout.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        AboutBox about = new AboutBox(_shlXmlOperations, XmlOperationsResources.METADATA_JSON);
        about.open(true);

        setFocus(_lastFocusedFileType);
      }
    });
    btnAbout.setToolTipText("About XML Operations\nPress F1 for help");

    splitSourceResult = new SashForm(compositeMain, SWT.NONE);
    splitSourceResult.setSashWidth(5);
    splitSourceResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    compositeXmlOthers = new Composite(splitSourceResult, SWT.NONE);
    compositeXmlOthers.setLayout(new FillLayout(SWT.VERTICAL));

    splitXmlOthers = new SashForm(compositeXmlOthers, SWT.VERTICAL);
    splitXmlOthers.setSashWidth(5);

    tabsXml = new TabFolder(splitXmlOthers, SWT.NONE);
    tabsXml.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDoubleClick(MouseEvent e)
      {
        if ((e.stateMask & SWT.CONTROL) == 0)
        {
          splitToRightOrDownOnDoubleClick(splitXmlOthers);
        }
        else
        {
          splitToRightOrDownOnDoubleClick(splitSourceResult);
        }
      }
    });
    tabsXml.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        textXml.setFocus();
      }
    });

    tabXml = new TabItem(tabsXml, SWT.NONE);
    tabXml.setToolTipText("Source document (Ctrl+0)");
    tabXml.setText("XML");

    textXml = new EnhancedStyledText(tabsXml, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    textXml.setAlwaysShowScrollBars(false);
    textXml.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXml.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXml.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.XML, e.keyCode, e.stateMask);
      }
    });
    textXml.setBackground(_editBackground);
    textXml.setTabs(TEXT_TAB_SIZE);
    textXml.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _xmlFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textXml.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastFocusedFileType = FileType.XML;
        setStatus();

        _lastSelectedForClipboard = textXml;
        _lastSelectedForClipboardUndoRedo = _xmlFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(true);

        btnNew.setToolTipText("New XML (Ctrl+N)");
        btnOpen.setToolTipText("Open XML (Ctrl+O)");
        btnSave.setToolTipText("Save XML (Ctrl+S)");
        btnSaveAs.setToolTipText("Save XML as");
      }
    });
    tabXml.setControl(textXml);

    tabsOthers = new TabFolder(splitXmlOthers, SWT.NONE);
    tabsOthers.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDoubleClick(MouseEvent e)
      {
        if ((e.stateMask & SWT.CONTROL) == 0)
        {
          splitToLeftOrUpOnDoubleClick(splitXmlOthers);
        }
        else
        {
          splitToRightOrDownOnDoubleClick(splitSourceResult);
        }
      }
    });
    tabsOthers.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        if (textXpath == null)
        {
          return;
        }

        switch (tabsOthers.getSelectionIndex())
        {
          case 0:
            textXpath.setFocus();
            break;

          case 1:
            textXslt.setFocus();
            break;

          case 2:
            textXquery.setFocus();
            break;

          case 3:
            textXsd.setFocus();
            break;

          case 4:
            textDtd.setFocus();
            break;
        }
      }
    });
    tabsOthers.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        if (textXpath == null)
        {
          return;
        }

        switch (tabsOthers.getSelectionIndex())
        {
          case 0:
            textXpath.setFocus();
            break;

          case 1:
            textXslt.setFocus();
            break;

          case 2:
            textXquery.setFocus();
            break;

          case 3:
            textXsd.setFocus();
            break;

          case 4:
            textDtd.setFocus();
            break;
        }
      }
    });

    tabXpath = new TabItem(tabsOthers, SWT.NONE);
    tabXpath.setToolTipText("XPath query (Ctrl+1)\r\nCheck the namespace prefixes (hit F4) if needed\r\nPress Enter to apply or hit F1 for instructions");
    tabXpath.setText("XPath");

    textXpath = new EnhancedStyledText(tabsOthers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
    textXpath.setBackground(_editBackground);
    textXpath.setAlwaysShowScrollBars(false);
    textXpath.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXpath.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXpath.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.XPATH, e.keyCode, e.stateMask);
      }
    });
    textXpath.addVerifyKeyListener(new VerifyKeyListener()
    {
      @Override
      public void verifyKey(VerifyEvent arg0)
      {
        if (arg0.keyCode == SWT.CR && (arg0.stateMask & SWT.CONTROL) == 0)
        {
          arg0.doit = false;
          applyXPath();
        }
      }
    });
    textXpath.setTabs(TEXT_TAB_SIZE);
    textXpath.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _xpathFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textXpath.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastFocusedFileType = _lastFocusedOperationType = FileType.XPATH;
        setStatus();

        _lastSelectedForClipboard = textXpath;
        _lastSelectedForClipboardUndoRedo = _xpathFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(false);

        btnNew.setToolTipText("New XPath (Ctrl+N)");
        btnOpen.setToolTipText("Open XPath (Ctrl+O)");
        btnSave.setToolTipText("Save XPath (Ctrl+S)");
        btnSaveAs.setToolTipText("Save XPath as");

        btnDropDownApply.setToolTipText("Apply XPath (F5)");
        menuItemApplyAndShowInBrowser.setEnabled(false);
        menuItemShowInBrowser.setEnabled(false);
      }
    });
    tabXpath.setControl(textXpath);

    tabXslt = new TabItem(tabsOthers, SWT.NONE);
    tabXslt.setToolTipText("XSLT transformation (Ctrl+2)\r\nUse files from disk when dealing with xsl:import");
    tabXslt.setText("XSLT");

    textXslt = new EnhancedStyledText(tabsOthers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    textXslt.setBackground(_editBackground);
    textXslt.setAlwaysShowScrollBars(false);
    textXslt.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXslt.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXslt.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.XSLT, e.keyCode, e.stateMask);
      }
    });
    textXslt.setTabs(TEXT_TAB_SIZE);
    textXslt.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _xsltFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textXslt.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastFocusedFileType = _lastFocusedOperationType = FileType.XSLT;
        setStatus();

        _lastSelectedForClipboard = textXslt;
        _lastSelectedForClipboardUndoRedo = _xsltFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(true);

        btnNew.setToolTipText("New XSLT (Ctrl+N)");
        btnOpen.setToolTipText("Open XSLT (Ctrl+O)");
        btnSave.setToolTipText("Save XSLT (Ctrl+S)");
        btnSaveAs.setToolTipText("Save XSLT as");

        btnDropDownApply.setToolTipText("Apply XSLT (F5)");
        menuItemApplyAndShowInBrowser.setEnabled(true);
        menuItemShowInBrowser.setEnabled(true);
      }
    });
    tabXslt.setControl(textXslt);

    tabXquery = new TabItem(tabsOthers, 0);
    tabXquery.setToolTipText("XQuery (Ctrl+3)");
    tabXquery.setText("XQuery");

    textXquery = new EnhancedStyledText(tabsOthers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    textXquery.setBackground(_editBackground);
    textXquery.setAlwaysShowScrollBars(false);
    textXquery.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXquery.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _xqueryFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textXquery.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.XQUERY, e.keyCode, e.stateMask);
      }
    });
    textXquery.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent arg0)
      {
        _lastFocusedFileType = _lastFocusedOperationType = FileType.XQUERY;
        setStatus();

        _lastSelectedForClipboard = textXquery;
        _lastSelectedForClipboardUndoRedo = _xqueryFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(false);

        btnNew.setToolTipText("New XQuery (Ctrl+N)");
        btnOpen.setToolTipText("Open XQuery (Ctrl+O)");
        btnSave.setToolTipText("Save XQuery (Ctrl+S)");
        btnSaveAs.setToolTipText("Save XQuery as");

        btnDropDownApply.setToolTipText("Apply XQuery (F5)");
        menuItemApplyAndShowInBrowser.setEnabled(true);
        menuItemShowInBrowser.setEnabled(true);
      }
    });
    textXquery.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXquery.setTabs(TEXT_TAB_SIZE);
    tabXquery.setControl(textXquery);

    tabXsd = new TabItem(tabsOthers, SWT.NONE);
    tabXsd.setToolTipText("XSD verification (Ctrl+4)\r\nUse files from disk when dealing with xsd:import");
    tabXsd.setText("XSD");

    textXsd = new EnhancedStyledText(tabsOthers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    textXsd.setBackground(_editBackground);
    textXsd.setAlwaysShowScrollBars(false);
    textXsd.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXsd.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textXsd.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.XSD, e.keyCode, e.stateMask);
      }
    });
    textXsd.setTabs(TEXT_TAB_SIZE);
    textXsd.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _xsdFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textXsd.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastFocusedFileType = _lastFocusedOperationType = FileType.XSD;
        setStatus();

        _lastSelectedForClipboard = textXsd;
        _lastSelectedForClipboardUndoRedo = _xsdFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(true);

        btnNew.setToolTipText("New XSD (Ctrl+N)");
        btnOpen.setToolTipText("Open XSD (Ctrl+O)");
        btnSave.setToolTipText("Save XSD (Ctrl+S)");
        btnSaveAs.setToolTipText("Save XSD as");

        btnDropDownApply.setToolTipText("Apply XSD (F5)");
        menuItemApplyAndShowInBrowser.setEnabled(false);
        menuItemShowInBrowser.setEnabled(false);
      }
    });
    tabXsd.setControl(textXsd);
    splitXmlOthers.setWeights(new int[] { 1, 1 });

    tabDtd = new TabItem(tabsOthers, SWT.NONE);
    tabDtd.setToolTipText("DTD verification (Ctrl+5)");
    tabDtd.setText("DTD");

    textDtd = new EnhancedStyledText(tabsOthers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    textDtd.setBackground(_editBackground);
    textDtd.setAlwaysShowScrollBars(false);
    textDtd.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textDtd.addModifyListener(new ModifyListener()
    {
      @Override
      public void modifyText(ModifyEvent arg0)
      {
        if (!_modifyingFile)
        {
          _dtdFileChanged = true;
        }
        else
        {
          _modifyingFile = false;
        }

        setStatus();
      }
    });
    textDtd.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(FileType.DTD, e.keyCode, e.stateMask);
      }
    });
    textDtd.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textDtd.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastFocusedFileType = _lastFocusedOperationType = FileType.DTD;
        setStatus();

        _lastSelectedForClipboard = textDtd;
        _lastSelectedForClipboardUndoRedo = _dtdFileUndoRedo;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(false);

        btnNew.setToolTipText("New DTD (Ctrl+N)");
        btnOpen.setToolTipText("Open DTD (Ctrl+O)");
        btnSave.setToolTipText("Save DTD (Ctrl+S)");
        btnSaveAs.setToolTipText("Save DTD as");

        btnDropDownApply.setToolTipText("Apply DTD (F5)");
        menuItemApplyAndShowInBrowser.setEnabled(false);
        menuItemShowInBrowser.setEnabled(false);
      }
    });
    textDtd.setTabs(2);
    tabDtd.setControl(textDtd);

    compositeResult = new Composite(splitSourceResult, SWT.NONE);
    compositeResult.setLayout(new FillLayout(SWT.HORIZONTAL));

    tabsResultText = new TabFolder(compositeResult, SWT.NONE);
    tabsResultText.setBackground(_editBackground);
    tabsResultText.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDoubleClick(MouseEvent e)
      {
        splitToLeftOrUpOnDoubleClick(splitSourceResult);
      }
    });
    tabsResultText.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        textResult.setFocus();
      }
    });

    tabResultText = new TabItem(tabsResultText, SWT.NONE);
    tabResultText.setToolTipText("Result in text format (Ctrl+R)");
    tabResultText.setText("Result");

    textResult = new EnhancedStyledText(tabsResultText, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
    textResult.setAlwaysShowScrollBars(false);
    textResult.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseDown(MouseEvent e)
      {
        setClipboardAndUndoEnable();
      }
    });
    textResult.addCaretListener(new CaretListener()
    {
      @Override
      public void caretMoved(CaretEvent arg0)
      {
        setClipboardAndUndoEnable();
      }
    });
    textResult.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _lastSelectedForClipboard = textResult;
        _lastSelectedForClipboardUndoRedo = null;
        setClipboardAndUndoEnable();

        btnFormat.setEnabled(false);
      }
    });
    textResult.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        onKeyEvent(null, e.keyCode, e.stateMask);
      }
    });
    textResult.setBackground(_editBackground);
    textResult.setTabs(TEXT_TAB_SIZE);
    tabResultText.setControl(textResult);
    splitSourceResult.setWeights(new int[] { 1, 1 });

    compositeStatusBar = new Composite(compositeMain, SWT.NONE);
    RowLayout rl_compositeStatusBar = new RowLayout(SWT.HORIZONTAL);
    rl_compositeStatusBar.fill = true;
    rl_compositeStatusBar.marginTop = 0;
    rl_compositeStatusBar.marginBottom = 0;
    compositeStatusBar.setLayout(rl_compositeStatusBar);
    GridData gd_compositeStatusBar = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_compositeStatusBar.heightHint = 15;
    compositeStatusBar.setLayoutData(gd_compositeStatusBar);

    lblStatus = new Label(compositeStatusBar, SWT.NONE);
    lblStatus.setLayoutData(new RowData(850, 15));
    lblStatus.setText("Ready");
  }

  /**
   * Load dropped file
   *
   * @param file
   *          Dropped file to load
   */
  private void loadDroppedFile(String file)
  {
    FileType fileType = getFileType(file);

    boolean fileChanged = hasFileChangedByDropping(fileType);

    if (fileChanged)
    {
      switch (SwtMessageBox.messageBox(String.format("The %s file has been modified. Do you want to save the current changes?", fileType), "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(fileType))
          {
            return;
          }
          else
          {
            break;
          }

        case SWT.NO:
          break;

        case SWT.CANCEL:
          setFocus(fileType);
          return;
      }
    }

    try
    {
      if (FileContent.hasBom(file))
      {
        switch (SwtMessageBox.messageBox(String.format(BOM_WARNING2, fileType), "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO))
        {
          case SWT.YES:
            file = Files.copyFileNoBom(file);
            break;

          case SWT.NO:
            break;
        }
      }
    }
    catch (IOException e)
    {
      SwtMessageBox.messageBoxError(String.format("File manipulation error (%s)", e.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(e));
    }

    setTextOfDroppedFile(file, fileType);

    _modifyingFile = false;
    setStatus();
    lblStatus.setText("Ready");
    setFocus(fileType);

    if (_lastSelectedForClipboardUndoRedo != null)
    {
      _lastSelectedForClipboardUndoRedo.reset();
      setClipboardAndUndoEnable();
    }
  }

  private void setTextOfDroppedFile(String file, FileType fileType)
  {
    try
    {
      _modifyingFile = true;

      switch (fileType)
      {
        case XML:
          textXml.setText(FileContent.readTextFile(file));
          textXml.setFocus();
          _xmlFile = file;
          _xmlFileChanged = false;
          break;

        case XPATH:
          textXpath.setText(FileContent.readTextFile(file));
          textXpath.setFocus();
          _xpathFile = file;
          _xpathFileChanged = false;
          break;

        case XSD:
          textXsd.setText(FileContent.readTextFile(file));
          textXsd.setFocus();
          _xsdFile = file;
          _xsdFileChanged = false;
          break;

        case XSLT:
          textXslt.setText(FileContent.readTextFile(file));
          textXslt.setFocus();
          _xsltFile = file;
          _xsltFileChanged = false;
          break;

        case XQUERY:
          textXquery.setText(FileContent.readTextFile(file));
          textXquery.setFocus();
          _xqueryFile = file;
          _xqueryFileChanged = false;
          break;

        case DTD:
          textDtd.setText(FileContent.readTextFile(file));
          textDtd.setFocus();
          _dtdFile = file;
          _dtdFileChanged = false;
          break;

        default:
          break;
      }
    }
    catch (StringException e)
    {
      SwtMessageBox.messageBoxError(String.format("Error while opening file (%s)", e.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(e));
    }
  }

  /**
   * Check if a file has changed as a result of a drop
   * 
   * @param fileType
   *          Type of file to check
   * 
   * @return true if a file has changed, otherwise false
   */
  private boolean hasFileChangedByDropping(FileType fileType)
  {
    boolean fileChanged;

    switch (fileType)
    {
      case XML:
        fileChanged = _xmlFileChanged;
        break;

      case XPATH:
        fileChanged = _xpathFileChanged;
        tabsOthers.setSelection(0);
        break;

      case XSLT:
        fileChanged = _xsltFileChanged;
        tabsOthers.setSelection(1);
        break;

      case XQUERY:
        fileChanged = _xqueryFileChanged;
        tabsOthers.setSelection(2);
        break;

      case XSD:
        fileChanged = _xsdFileChanged;
        tabsOthers.setSelection(3);
        break;

      case DTD:
        fileChanged = _dtdFileChanged;
        tabsOthers.setSelection(4);
        break;

      default:
        return false;
    }

    return fileChanged;
  }

  /**
   * Get file type
   * 
   * @param file
   *          File to get the type for
   * 
   * @return Type of input file
   */
  private FileType getFileType(String file)
  {
    String extension = FilenameUtils.getExtension(file);
    if (extension.equals("nobom"))
    {
      extension = FilenameUtils.getExtension(file.substring(0, file.length() - ".nobom".length()));
    }

    switch (extension)
    {
      case "xml":
      case "config":
      case "csproj":
        return FileType.XML;

      case "xpath":
        return FileType.XPATH;

      case "xsl":
      case "xslt":
        return FileType.XSLT;

      case "xsd":
        return FileType.XSD;

      case "dtd":
        return FileType.DTD;

      case "xquery":
      case "xq":
      case "xqy":
        return FileType.XQUERY;
    }

    return FileType.XML;
  }

  /**
   * Open file
   *
   * @param fileType
   *          Type of the file
   */
  private void fileOpen(FileType fileType)
  {
    boolean fileChanged = hasFileChanged(fileType);

    if (fileChanged)
    {
      switch (SwtMessageBox.messageBox("Do you want to save the current changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(fileType))
          {
            return;
          }
          else
          {
            break;
          }

        case SWT.NO:
          break;

        case SWT.CANCEL:
          setFocus(fileType);
          return;
      }
    }

    String[] filters = getFiltersByFileType(fileType);

    String[] target = SwtFiles.openFiles(null, null, "Open", filters, SWT.OPEN | SWT.SINGLE);

    if (target == null)
    {
      setFocus(fileType);
      return;
    }

    try
    {
      if (FileContent.hasBom(target[0]))
      {
        switch (SwtMessageBox.messageBox(BOM_WARNING, "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO))
        {
          case SWT.YES:
            target[0] = Files.copyFileNoBom(target[0]);
            break;

          case SWT.NO:
            break;
        }
      }
    }
    catch (IOException e)
    {
      SwtMessageBox.messageBoxError(String.format("File manipulation error (%s)", e.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(e));
    }

    setTextOfOpenedFile(fileType, target);

    _modifyingFile = false;
    setStatus();
    lblStatus.setText("Ready");
    setFocus(fileType);

    if (_lastSelectedForClipboardUndoRedo != null)
    {
      _lastSelectedForClipboardUndoRedo.reset();
      setClipboardAndUndoEnable();
    }
  }

  private void setTextOfOpenedFile(FileType fileType, String[] target)
  {
    try
    {
      _modifyingFile = true;

      switch (fileType)
      {
        case XML:
          textXml.setText(FileContent.readTextFile(target[0]));
          textXml.setFocus();
          _xmlFile = target[0];
          _xmlFileChanged = false;
          break;

        case XPATH:
          textXpath.setText(FileContent.readTextFile(target[0]));
          textXpath.setFocus();
          _xpathFile = target[0];
          _xpathFileChanged = false;
          break;

        case XSD:
          textXsd.setText(FileContent.readTextFile(target[0]));
          textXsd.setFocus();
          _xsdFile = target[0];
          _xsdFileChanged = false;
          break;

        case XSLT:
          textXslt.setText(FileContent.readTextFile(target[0]));
          textXslt.setFocus();
          _xsltFile = target[0];
          _xsltFileChanged = false;
          break;

        case XQUERY:
          textXquery.setText(FileContent.readTextFile(target[0]));
          textXquery.setFocus();
          _xqueryFile = target[0];
          _xqueryFileChanged = false;
          break;

        case DTD:
          textDtd.setText(FileContent.readTextFile(target[0]));
          textDtd.setFocus();
          _dtdFile = target[0];
          _dtdFileChanged = false;
          break;

        default:
          break;
      }

      resetResultText();
    }
    catch (StringException e)
    {
      SwtMessageBox.messageBoxError(String.format("Error while opening file (%s)", e.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(e));
    }
  }

  private String[] getFiltersByFileType(FileType fileType)
  {
    String[] filters = null;

    switch (fileType)
    {
      case XML:
        filters = new String[] { "*.xml", "*.xml.nobom", "*" };
        break;

      case XPATH:
        filters = new String[] { "*.xpath", "*.xpath.nobom", "*.txt", "*" };
        break;

      case XSD:
        filters = new String[] { "*.xsd", "*.xsd.nobom", "*" };
        break;

      case XSLT:
        filters = new String[] { "*.xsl", "*.xslt", "*.xsl.nobom", "*.xslt.nobom", "*" };
        break;

      case XQUERY:
        filters = new String[] { "*.xquery", "*.xq", "*.xqy", "*.xquery.nobom", "*.xq.nobom", "*.xqy.nobom", "*" };
        break;

      case DTD:
        filters = new String[] { "*.dtd", "*.dtd.nobom", "*" };
        break;

      default:
        break;
    }
    return filters;
  }

  /**
   * Check if a file has changed as a result of an opening
   * 
   * @param fileType
   *          Type of file to check
   * 
   * @return true if a file has changed, otherwise false
   */
  private boolean hasFileChanged(FileType fileType)
  {
    boolean fileChanged;

    switch (fileType)
    {
      case XML:
        fileChanged = _xmlFileChanged;
        break;

      case XPATH:
        fileChanged = _xpathFileChanged;
        break;

      case XSD:
        fileChanged = _xsdFileChanged;
        break;

      case XSLT:
        fileChanged = _xsltFileChanged;
        break;

      case XQUERY:
        fileChanged = _xqueryFileChanged;
        break;

      case DTD:
        fileChanged = _dtdFileChanged;
        break;

      default:
        return false;
    }

    return fileChanged;
  }

  /**
   * Save file
   *
   * @param fileType
   *          Type of the file
   *
   * @return true if successfully saving, otherwise false
   */
  private boolean fileSave(FileType fileType)
  {
    switch (fileType)
    {
      case XML:
        if (_xmlFileChanged)
        {
          if (_xmlFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_xmlFile, fileType);
          }
        }
        break;

      case XPATH:
        if (_xpathFileChanged)
        {
          if (_xpathFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_xpathFile, fileType);
          }
        }
        break;

      case XSD:
        if (_xsdFileChanged)
        {
          if (_xsdFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_xsdFile, fileType);
          }
        }
        break;

      case DTD:
        if (_dtdFileChanged)
        {
          if (_dtdFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_dtdFile, fileType);
          }
        }
        break;

      case XSLT:
        if (_xsltFileChanged)
        {
          if (_xsltFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_xsltFile, fileType);
          }
        }
        break;

      case XQUERY:
        if (_xqueryFileChanged)
        {
          if (_xqueryFile.length() == 0)
          {
            return fileSaveAs(fileType);
          }
          else
          {
            return saveFileOnDisk(_xqueryFile, fileType);
          }
        }
        break;

      default:
        break;
    }

    setFocus(fileType);
    return false;
  }

  /**
   * Save file
   *
   * @param fileType
   *          Type of the file
   *
   * @return true if the operation was completed, otherwise false
   */
  private boolean fileSaveAs(FileType fileType)
  {
    String[] filters = null;

    switch (_lastFocusedFileType)
    {
      case XML:
        filters = new String[] { "*.xml", "*" };
        break;

      case XPATH:
        filters = new String[] { "*.xpath", "*.txt", "*" };
        break;

      case XSD:
        filters = new String[] { "*.xsd", "*" };
        break;

      case DTD:
        filters = new String[] { "*.dtd", "*" };
        break;

      case XSLT:
        filters = new String[] { "*.xsl", "*.xslt", "*" };
        break;

      case XQUERY:
        filters = new String[] { "*.xquery", "*.xq", "*.xqy", "*" };
        break;

      default:
        break;
    }

    String[] target = SwtFiles.openFiles(null, null, "Save As", filters, SWT.SAVE | SWT.SINGLE);

    if (target == null)
    {
      setFocus(fileType);
      return false;
    }

    if (Files.exists(target[0]))
    {
      if (SwtMessageBox.messageBox("The file already exists, overwrite?", "XML Operations", SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL) == SWT.CANCEL)
      {
        setFocus(fileType);
        return false;
      }
    }

    return saveFileOnDisk(target[0], fileType);
  }

  /**
   * Save file on disk
   *
   * @param destinationFile
   *          File to save into
   * @param fileType
   *          of the file to be saved
   *
   * @return true if saved successfully, otherwise false
   */
  private boolean saveFileOnDisk(String destinationFile, FileType fileType)
  {
    try
    {
      switch (fileType)
      {
        case XML:
          FileContent.writeTextFile(textXml.getText(), _xmlFile = destinationFile);
          _xmlFileChanged = false;
          break;

        case XPATH:
          FileContent.writeTextFile(textXpath.getText(), _xpathFile = destinationFile);
          _xpathFileChanged = false;
          break;

        case XSD:
          FileContent.writeTextFile(textXsd.getText(), _xsdFile = destinationFile);
          _xsdFileChanged = false;
          break;

        case DTD:
          FileContent.writeTextFile(textDtd.getText(), destinationFile);
          if (destinationFile != DTD_TEMP_FILE)
          {
            _dtdFile = destinationFile;
            _dtdFileChanged = false;
          }
          break;

        case XSLT:
          FileContent.writeTextFile(textXslt.getText(), _xsltFile = destinationFile);
          _xsltFileChanged = false;
          break;

        case XQUERY:
          FileContent.writeTextFile(textXquery.getText(), _xqueryFile = destinationFile);
          _xqueryFileChanged = false;
          break;

        default:
          break;
      }
    }
    catch (StringException e)
    {
      SwtMessageBox.messageBoxError(String.format("Error while saving file (%s)", e.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(e));

      return false;
    }
    finally
    {
      setStatus();
      setFocus(fileType);
    }

    return true;
  }

  /**
   * New file
   *
   * @param fileType
   *          Type of file
   */
  private void fileNew(FileType fileType)
  {
    boolean fileChanged = hasFileChanged(fileType);

    if (fileChanged)
    {
      switch (SwtMessageBox.messageBox("Do you want to save the current changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(fileType))
          {
            return;
          }
          else
          {
            break;
          }

        case SWT.NO:
          break;

        case SWT.CANCEL:
          return;
      }
    }

    setTextOfNewFile(fileType);

    setStatus();
    lblStatus.setText("Ready");

    if (_lastSelectedForClipboardUndoRedo != null)
    {
      _lastSelectedForClipboardUndoRedo.reset();
      setClipboardAndUndoEnable();
    }
  }

  private void setTextOfNewFile(FileType fileType)
  {
    _modifyingFile = true;

    switch (fileType)
    {
      case XML:
        textXml.setText("");
        textXml.setFocus();
        _xmlFileChanged = false;
        _xmlFile = "";
        break;

      case XPATH:
        textXpath.setText("");
        textXpath.setFocus();
        _xpathFileChanged = false;
        _xpathFile = "";
        break;

      case XSD:
        textXsd.setText("");
        textXsd.setFocus();
        _xsdFileChanged = false;
        _xsdFile = "";
        break;

      case DTD:
        textDtd.setText("");
        textDtd.setFocus();
        _dtdFileChanged = false;
        _dtdFile = "";
        break;

      case XSLT:
        textXslt.setText("");
        textXslt.setFocus();
        _xsltFileChanged = false;
        _xsltFile = "";
        break;

      case XQUERY:
        textXquery.setText("");
        textXquery.setFocus();
        _xqueryFileChanged = false;
        _xqueryFile = "";
        break;

      default:
        break;
    }
  }

  /**
   * Set status.
   */
  private void setStatus()
  {
    switch (_lastFocusedFileType)
    {
      case XML:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _xmlFileChanged ? "* " : "", _xmlFile.length() == 0 ? "XML" : _xmlFile));
        btnSave.setEnabled(_xmlFileChanged);
        break;

      case XPATH:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _xpathFileChanged ? "* " : "", _xpathFile.length() == 0 ? "XPath" : _xpathFile));
        btnSave.setEnabled(_xpathFileChanged);
        break;

      case XSD:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _xsdFileChanged ? "* " : "", _xsdFile.length() == 0 ? "XSD" : _xsdFile));
        btnSave.setEnabled(_xsdFileChanged);
        break;

      case DTD:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _dtdFileChanged ? "* " : "", _dtdFile.length() == 0 ? "DTD" : _dtdFile));
        btnSave.setEnabled(_dtdFileChanged);
        break;

      case XSLT:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _xsltFileChanged ? "* " : "", _xsltFile.length() == 0 ? "XSLT" : _xsltFile));
        btnSave.setEnabled(_xsltFileChanged);
        break;

      case XQUERY:
        _shlXmlOperations.setText(String.format(XML_OPERATIONS_TITLE, _xqueryFileChanged ? "* " : "", _xqueryFile.length() == 0 ? "XQuery" : _xqueryFile));
        btnSave.setEnabled(_xqueryFileChanged);
        break;

      default:
        break;
    }
  }

  /**
   * Print error in the result text area
   *
   * @param fileType
   *          File type
   * @param error
   *          Error message
   */
  private void printError(FileType fileType, String error)
  {
    if (error == null)
    {
      error = "Unknown error";
    }

    textResult.setForeground(COLOR_RED);

    int errorLine = -1;

    switch (fileType)
    {
      case XPATH:
        textXpath.setFocus();
        break;

      case XQUERY:
        error = printXqueryError(error, errorLine);
        break;

      default:
        printGeneralTypeError(fileType, error);
        break;
    }

    textResult.setText(error);
  }

  private void printGeneralTypeError(FileType fileType, String error)
  {
    int errorLine;
    int lineColPosEnd = error.indexOf(":");
    if (lineColPosEnd != -1)
    {
      String lineCol = error.substring(0, lineColPosEnd);

      String[] lineColPair;
      if (lineCol.startsWith("line "))
      {
        lineColPair = lineCol.split(" ");
        if (lineColPair.length == 2)
        {
          lineColPair[0] = lineColPair[1];
        }
      }
      else
      {
        lineColPair = lineCol.split(",");
      }

      if (lineColPair.length == 2)
      {
        errorLine = 1;

        try
        {
          errorLine = Integer.parseInt(lineColPair[0]);
        }
        catch (NumberFormatException e)
        {
        }

        selectErrorLine(fileType, errorLine);
      }
    }
  }

  private String printXqueryError(String error, int errorLine)
  {
    if (error.indexOf("Stopped at") == 0)
    {
      error = error.substring(error.indexOf('\n') + 1);
    }

    if (error.indexOf("Error: ") == 0)
    {
      error = error.substring("Error: ".length());
    }

    if (error.indexOf("[") == 0)
    {
      int closingSquare = error.indexOf(']');
      int firstSpace = error.indexOf(' ');

      if (closingSquare != -1 && closingSquare < firstSpace)
      {
        error = error.substring(closingSquare + 2);
      }
    }

    String[] lines = error.split("\n");
    String[] tokens = lines[0].split("[ ,\\.]");
    for (int i = 0; i < tokens.length - 1; i++)
    {
      if (tokens[i].equals("Line:") || tokens[i].equals("line"))
      {
        try
        {
          errorLine = Integer.parseInt(tokens[i + 1]);
          break;
        }
        catch (NumberFormatException ex)
        {
        }
      }
    }

    if (errorLine != -1)
    {
      selectErrorLine(FileType.XQUERY, errorLine);
    }

    return error;
  }

  /**
   * Select error line
   *
   * @param fileType
   *          Type of the file to select the error line into
   * @param errorLine
   *          Error line
   */
  private void selectErrorLine(FileType fileType, int errorLine)
  {
    EnhancedStyledText theText = textXml;
    switch (fileType)
    {
      case XML:
        theText = textXml;
        break;

      case XSD:
        theText = textXsd;
        break;

      case DTD:
        theText = textDtd;
        break;

      case XSLT:
        theText = textXslt;
        break;

      case XQUERY:
        theText = textXquery;
        break;

      default:
        break;
    }

    int offset = 0;
    String[] rows = theText.getText().split("\n");

    errorLine = Math.min(Math.max(errorLine, 1), rows.length);
    for (int i = 0; i < errorLine - 1; i++)
    {
      offset += rows[i].length() + 1;
    }
    int len = rows[errorLine - 1].length() - (rows[errorLine - 1].endsWith("\r") ? 1 : 0);

    theText.setSelection(offset, offset + len);
    theText.setFocus();
  }

  /**
   * Autosave before operating
   *
   * @param fileType
   *          Type of the file to be saved
   *
   * @return true if successfully saved, otherwise false
   */
  private boolean autoSave(FileType fileType)
  {
    if (btnAutoSave.getSelection())
    {
      if (!saveFileOnDisk(_xmlFile, FileType.XML))
      {
        return false;
      }

      switch (fileType)
      {
        case DTD:
          if (_dtdFile.length() != 0 && _dtdFileChanged)
          {
            if (!saveFileOnDisk(_dtdFile, FileType.DTD))
            {
              return false;
            }
          }
          break;

        case XPATH:
          if (_xpathFile.length() != 0 && _xpathFileChanged)
          {
            if (!saveFileOnDisk(_xpathFile, FileType.XPATH))
            {
              return false;
            }
          }
          break;

        case XSD:
          if (_xsdFile.length() != 0 && _xsdFileChanged)
          {
            if (!saveFileOnDisk(_xsdFile, FileType.XSD))
            {
              return false;
            }
          }
          break;

        case XSLT:
          if (_xsltFile.length() != 0 && _xsltFileChanged)
          {
            if (!saveFileOnDisk(_xsltFile, FileType.XSLT))
            {
              return false;
            }
          }
          break;

        case XQUERY:
          if (_xqueryFile.length() != 0 && _xqueryFileChanged)
          {
            if (!saveFileOnDisk(_xqueryFile, FileType.XQUERY))
            {
              return false;
            }
          }
          break;

        default:
          return false;
      }
    }

    return true;
  }

  /**
   * Apply XPath
   */
  private void applyXPath()
  {
    long startTime = System.currentTimeMillis();
    lblStatus.setText("Ready");

    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      if (!autoSave(FileType.XPATH))
      {
        return;
      }

      Xml xml = null;

      try
      {
        xml = new Xml();
        xml.load(textXml.getText());
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      NodeList nodes = null;

      try
      {
        nodes = xml.selectNodesWithDefaultNamespaces(SwtText.getCurrentLine(textXpath));

        textResult.setForeground(COLOR_BLACK);

        StringBuilder sb = new StringBuilder();
        int numNodes = nodes.getLength();

        if (numNodes == 0)
        {
          sb.append("Empty selection");
        }
        else
        {
          sb.append(String.format("%d node%s selected\n\n%s", numNodes, numNodes == 1 ? "" : "s", numNodes == 1 ? "" : "-1-\n\n"));
        }

        for (int i = 0; i < numNodes; i++)
        {
          String xmlText = xml.nodeToString(nodes.item(i), false, true);
          if (!xmlText.endsWith("\n"))
          {
            xmlText += "\n";
          }
          sb.append(xmlText);
          sb.append(i == numNodes - 1 ? "" : String.format("\n-%d-\n\n", i + 2));
        }

        textResult.setText(sb.toString());
      }
      catch (StringException e)
      {
        try
        {
          String stringResult = xml.selectStringWithDefaultNamespaces(SwtText.getCurrentLine(textXpath));

          textResult.setForeground(COLOR_BLACK);
          textResult.setText(stringResult);

          return;
        }
        catch (StringException e2)
        {
          try
          {
            double doubleValue = xml.selectDoubleWithDefaultNamespaces(SwtText.getCurrentLine(textXpath));

            textResult.setForeground(COLOR_BLACK);

            if (Maths.isInteger(doubleValue))
            {
              textResult.setText(String.valueOf((int) doubleValue));
            }
            else
            {
              textResult.setText(String.valueOf(doubleValue));
            }

            return;
          }
          catch (StringException e3)
          {
            // no more XPath return types, back to the original exception (below)
          }
        }

        printError(FileType.XPATH, e.getMessage());
        lblStatus.setText(printErrorTime());
      }
    }
    finally
    {
      lblStatus.setText(printTimeDifference("XPath", startTime));

      _shlXmlOperations.setCursor(defaultCursor);
    }
  }

  /**
   * Verify by XSD
   */
  private void verifyByXsd()
  {
    long startTime = System.currentTimeMillis();
    lblStatus.setText("Ready");

    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      if (!autoSave(FileType.XSD))
      {
        return;
      }

      Xml xml = null;

      try
      {
        xml = new Xml();
        xml.load(textXml.getText());
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      try
      {
        if (_xsdFile.length() > 0 && !_xsdFileChanged)
        {
          xml.validateAgainstXsdFile(_xsdFile);
        }
        else
        {
          xml.validateAgainstXsd(textXsd.getText());
        }

        lblStatus.setText(printTimeDifference("XSD", startTime));

        textResult.setForeground(COLOR_GREEN);
        textResult.setText("XML valid by schema");
      }
      catch (StringException e)
      {
        printError(FileType.XSD, e.getMessage());
        lblStatus.setText(printErrorTime());
      }
    }
    finally
    {
      _shlXmlOperations.setCursor(defaultCursor);
    }
  }

  /**
   * Verify by DTD
   */
  private void verifyByDtd()
  {
    long startTime = System.currentTimeMillis();
    lblStatus.setText("Ready");

    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      if (!autoSave(FileType.DTD))
      {
        return;
      }

      boolean inline = textDtd.getText().trim().length() == 0;

      Xml xml = null;

      try
      {
        xml = new Xml(inline);
        xml.load(textXml.getText());
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      if (!inline)
      {
        Property<Boolean> dtdError = new Property<Boolean>();

        try
        {
          if (_dtdFile.length() > 0 && !_dtdFileChanged)
          {
            xml.validateAgainstDtdFile(_dtdFile, dtdError);
          }
          else
            if (saveFileOnDisk(DTD_TEMP_FILE, FileType.DTD))
            {
              xml.validateAgainstDtdFile(DTD_TEMP_FILE, dtdError);
            }
            else
            {
              return;
            }
        }
        catch (StringException e)
        {
          printError(dtdError.get() ? FileType.DTD : FileType.XML, e.getMessage());
          lblStatus.setText(printErrorTime());

          return;
        }
      }

      lblStatus.setText(printTimeDifference("DTD", startTime));

      textResult.setForeground(COLOR_GREEN);
      textResult.setText("XML valid by document type");
    }
    finally
    {
      _shlXmlOperations.setCursor(defaultCursor);
    }
  }

  /**
   * Apply XSLT
   *
   * @param viewInBrowser
   *          true to view in browser, otherwise false
   */
  private void applyXslt(boolean viewInBrowser)
  {
    long startTime = System.currentTimeMillis();
    lblStatus.setText("Ready");

    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      if (!autoSave(FileType.XSLT))
      {
        return;
      }

      Xml xml = null;

      try
      {
        xml = new Xml();
        xml.load(textXml.getText());
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      try
      {
        String result;

        if (_xsltFile.length() > 0 && !_xsltFileChanged)
        {
          result = xml.applyXsltFile(_xsltFile);
        }
        else
        {
          result = xml.applyXslt(textXslt.getText());
        }

        if (result != null)
        {
          lblStatus.setText(printTimeDifference("XSLT", startTime));

          textResult.setForeground(COLOR_BLACK);

          textResult.setText(result);
        }
      }
      catch (StringException e)
      {
        printError(FileType.XSLT, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }
    }
    finally
    {
      _shlXmlOperations.setCursor(defaultCursor);
    }

    if (viewInBrowser)
    {
      viewResultInExternalBrowser();
    }
  }

  /**
   * Apply XQuery
   *
   * @param viewInBrowser
   *          true to see the result in external browser, otherwise false
   */
  private void applyXQuery(boolean viewInBrowser)
  {
    long startTime = System.currentTimeMillis();
    lblStatus.setText("Ready");

    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      if (!autoSave(FileType.XQUERY))
      {
        return;
      }

      Xml xml = null;
      String xmlText = textXml.getText().trim();

      try
      {
        xml = new Xml();

        if (xmlText.length() != 0)
        {
          xml.load(xmlText);
        }
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      try
      {
        if (_xmlFile.length() > 0 && !_xmlFileChanged)
        {
          xml.setFilePath(_xmlFile);
          xml.applyXQuery(textXquery.getText(), true);
        }
        else
        {
          xml.applyXQuery(textXquery.getText(), false);
        }
      }
      catch (StringException e)
      {
        printError(FileType.XQUERY, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }

      StringBuilder sbText = new StringBuilder();
      StringBuilder sbHtml = new StringBuilder();

      if (!analyzeXqueryResults(startTime, xml, sbText, sbHtml))
      {
        return;
      }
    }
    finally
    {
      _shlXmlOperations.setCursor(defaultCursor);
    }

    if (viewInBrowser)
    {
      viewResultInExternalBrowser();
    }
  }

  /**
   * @return true to continue, false to return
   */
  private boolean analyzeXqueryResults(long startTime, Xml xml, StringBuilder sbText, StringBuilder sbHtml)
  {
    if (xml.hasXQueryResults())
    {
      int i = 1;
      String firstItemText = "";

      Property<String> itemTextProp = new Property<String>();

      try
      {
        for (; xml.getNextXQueryResultItem(itemTextProp); i++)
        {
          String itemText = itemTextProp.get();

          if (!itemText.endsWith("\n"))
          {
            itemText += "\n";
          }

          switch (i)
          {
            case 1:
              firstItemText = itemText;
              break;

            case 2:
              sbText.append("\n-1-\n\n");
              sbText.append(firstItemText);
              sbHtml.append(firstItemText);

            default:
              sbText.append(String.format("\n-%d-\n\n", i));
              sbText.append(itemText);
              sbHtml.append(itemText);
              break;
          }
        }
      }
      catch (StringException e)
      {
        printError(FileType.XQUERY, e.getMessage());
        lblStatus.setText(printErrorTime());

        return false;
      }

      if (i == 1)
      {
        sbText.append("Empty selection");
      }
      else
      {
        if (i == 2)
        {
          sbText.append(firstItemText);
          sbHtml.append(firstItemText);
        }

        sbText.insert(0, String.format("%d node%s selected\n%s", i - 1, i == 2 ? "" : "s", i == 2 ? "\n" : ""));
      }
    }
    else
    {
      sbText.append("Empty selection");
    }

    lblStatus.setText(printTimeDifference("XQuery", startTime));

    textResult.setForeground(COLOR_BLACK);

    textResult.setText(sbText.toString());

    return true;
  }

  /**
   * Handle key event
   *
   * @param textboxType
   *          Type of the textbox the event happened into, or null
   * @param keyCode
   *          Key code
   * @param mask
   *          Key event mask
   */
  private void onKeyEvent(FileType textboxType, int keyCode, int mask)
  {
    switch (keyCode)
    {
      case SWT.F1:
        Program.launch(Platform.CURRENT_DIRECTORY + "help.html");
        break;

      case SWT.F5:
        switch (_lastFocusedOperationType)
        {
          case XPATH:
            applyXPath();
            break;

          case XSD:
            verifyByXsd();
            break;

          case DTD:
            verifyByDtd();
            break;

          case XSLT:
            applyXslt((mask & SWT.CONTROL) != 0);
            break;

          case XQUERY:
            applyXQuery((mask & SWT.CONTROL) != 0);
            break;

          default:
            break;
        }
        break;

      case SWT.F6:
        switch (_lastFocusedOperationType)
        {
          case XSLT:
          case XQUERY:
            viewResultInExternalBrowser();
            break;

          default:
            break;
        }
        break;

      case SWT.F4:
        printNamespaces();
        break;

      case SWT.F3:
        if (_lastSelectedForClipboard.getSelectionText().length() > 0)
        {
          textSearch.setText(_lastSelectedForClipboard.getSelectionText());
        }

        search((mask & SWT.CONTROL) != 0);
        break;

      case SWT.F8:
        if ((mask & SWT.ALT) != 0 && btnFormat.getEnabled())
        {
          formatXml();
        }
        break;

      case 'f':
        if ((mask & SWT.CONTROL) != 0)
        {
          if (_lastSelectedForClipboard.getSelectionText().length() > 0)
          {
            textSearch.setText(_lastSelectedForClipboard.getSelectionText());
          }

          textSearch.setFocus();
        }
        break;

      case 'n':
        if ((mask & SWT.CONTROL) != 0)
        {
          fileNew(_lastFocusedFileType);
        }
        break;

      case 'o':
        if ((mask & SWT.CONTROL) != 0)
        {
          fileOpen(_lastFocusedFileType);
        }
        break;

      case 's':
        if ((mask & SWT.CONTROL) != 0)
        {
          fileSave(_lastFocusedFileType);
        }
        break;

      case 'a':
        if ((mask & SWT.CONTROL) != 0)
        {
          _lastSelectedForClipboard.selectAll();
          setClipboardAndUndoEnable();
        }
        break;

      case '0':
        if ((mask & SWT.CONTROL) != 0)
        {
          textXml.setFocus();
        }
        break;

      case '1':
        if ((mask & SWT.CONTROL) != 0)
        {
          tabsOthers.setSelection(0);
          textXpath.setFocus();
        }
        break;

      case '2':
        if ((mask & SWT.CONTROL) != 0)
        {
          tabsOthers.setSelection(1);
          textXslt.setFocus();
        }
        break;

      case '3':
        if ((mask & SWT.CONTROL) != 0)
        {
          tabsOthers.setSelection(2);
          textXquery.setFocus();
        }
        break;

      case '4':
        if ((mask & SWT.CONTROL) != 0)
        {
          tabsOthers.setSelection(3);
          textXsd.setFocus();
        }
        break;

      case '5':
        if ((mask & SWT.CONTROL) != 0)
        {
          tabsOthers.setSelection(4);
          textDtd.setFocus();
        }
        break;

      case 'r':
        if ((mask & SWT.CONTROL) != 0)
        {
          textResult.setFocus();
        }
        break;

      case SWT.SPACE:
        if (textboxType != null && (mask & SWT.CONTROL) != 0)
        {
          switch (textboxType)
          {
            case XML:
              doXmlSuggestions(textboxType, textXml);
              break;

            case XPATH:
              doXmlSuggestions(textboxType, textXpath);
              break;

            case XSLT:
              doXmlSuggestions(textboxType, textXslt);
              break;

            case XQUERY:
              doXmlSuggestions(textboxType, textXquery);
              break;

            case XSD:
              doXmlSuggestions(textboxType, textXsd);
              break;

            case DTD:
              // no action yet

            default:
              break;
          }
        }

        break;
    }
  }

  /**
   * XML suggestions while editing a textbox
   *
   * @param textboxType
   *          Type of the textbox the suggestions have been asked from
   * @param textbox
   *          Textbox the suggestions have been asked from
   */
  private void doXmlSuggestions(FileType textboxType, final EnhancedStyledText textbox)
  {
    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      String text = textbox.getText();
      final Point selection = textbox.getSelection();

      if (selection.x > 0 && text.charAt(selection.x - 1) == '>')
      {
        int k = -1;
        for (int i = selection.x - 2; i >= 0; i--)
        {
          if (text.charAt(i) == '<')
          {
            if (i + 1 < text.length())
            {
              if (text.charAt(i + 1) == '/')
              {
                break;
              }
            }

            k = i;
            break;
          }
        }

        if (k != -1)
        {
          SwtText.insertIntoTextboxAndMoveCaretAtFront(textbox, String.format("</%s>", text.substring(k + 1, selection.x - 1).split(" ")[0]));
        }
      }
      else
        if (textboxType != FileType.XML)
        {
          if (textXml.getText().trim().length() == 0)
          {
            return;
          }

          int k = -1;
          for (int i = selection.x - 1; i >= 0; i--)
          {
            char c = text.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c) && "-_.".indexOf(c) == -1)
            {
              k = i;
              break;
            }
          }
          final int prefixBegin = ++k;

          Xml doc = null;

          try
          {
            doc = new Xml();
            doc.load(textXml.getText());

            ArrayList<String> allEntities = doc.getAllAttributes(text.substring(k, selection.x));

            boolean getTags = true;

            if (prefixBegin > 0)
            {
              if (text.charAt(prefixBegin - 1) == '@')
              {
                getTags = false;
              }
            }

            if (getTags)
            {
              ArrayList<String> allTags;

              switch (textboxType)
              {
                case XPATH:
                  allTags = doc.getAllTagsWithExtra(text.substring(k, selection.x), _xpathTags, "xpath:");
                  break;

                case XSLT:
                  allTags = doc.getAllTagsWithExtra(text.substring(k, selection.x), _xsltTags, "xsl:");
                  break;

                case XQUERY:
                  allTags = doc.getAllTagsWithExtra(text.substring(k, selection.x), _xqueryTags, "xq:");
                  break;

                default:
                  allTags = doc.getAllTags(text.substring(k, selection.x), null, null);
                  break;
              }

              allTags.addAll(allEntities);
              allEntities = allTags;
            }

            int allCount = allEntities.size();

            if (allCount == 0)
            {
              return;
            }

            if (allCount == 1)
            {
              insertXmlEntityAsSuggested(allEntities.get(0), textbox, selection, prefixBegin);
            }
            else
            {
              Menu menu = new Menu(textbox);

              for (int i = 0; i < allCount; i++)
              {
                final MenuItem mi = new MenuItem(menu, SWT.NONE);
                mi.setText(allEntities.get(i));

                mi.addSelectionListener(new SelectionListener()
                {
                  @Override
                  public void widgetSelected(SelectionEvent arg0)
                  {
                    insertXmlEntityAsSuggested(mi.getText(), textbox, selection, prefixBegin);
                  }

                  @Override
                  public void widgetDefaultSelected(SelectionEvent arg0)
                  {
                  }
                });
              }

              menu.setLocation(SwtPoints.translate(textbox.toDisplay(textbox.getCaret().getLocation()), 3, 2));
              menu.setVisible(true);
            }
          }
          catch (StringException e)
          {
            printError(FileType.XML, e.getMessage());
            lblStatus.setText(printErrorTime());

            return;
          }
        }
    }
    finally
    {
      setFocus(_lastFocusedFileType);
      _shlXmlOperations.setCursor(defaultCursor);
    }
  }

  /**
   * Insert suggested entity at the current selection position in a textbox
   *
   * @param entityName
   *          Entity name
   * @param textbox
   *          Textbox to insert into
   * @param selection
   *          Selection beginning position
   * @param prefixBegin
   *          Beginning of the detected prefix
   */
  private static void insertXmlEntityAsSuggested(String entityName, final EnhancedStyledText textbox, final Point selection, final int prefixBegin)
  {
    if (entityName.charAt(0) == '@')
    {
      entityName = entityName.substring(1);
    }
    else
    {
      String[] textParts = entityName.split(":", 2);
      entityName = textParts.length > 1 ? textParts[1] : textParts[0];
    }

    SwtText.insertIntoTextboxAndMoveCaret(textbox, entityName.substring(selection.x - prefixBegin));
  }

  /**
   * Format XML textbox
   */
  private void formatXml()
  {
    switch (_lastFocusedFileType)
    {
      case XML:
        formatTextBox(textXml);
        break;

      case XSD:
        formatTextBox(textXsd);
        break;

      case XSLT:
        formatTextBox(textXslt);
        break;

      default:
        break;
    }
  }

  /**
   * Format textbox
   * 
   * @param textBox
   *          Textbox to format
   */
  private void formatTextBox(EnhancedStyledText textBox)
  {
    try
    {
      String text = textBox.getText();

      text = Xml.format(text);

      textBox.setText(text);

      resetResultText();
    }
    catch (Throwable e)
    {
      printError(FileType.XML, e.getMessage());
      lblStatus.setText(printErrorTime());
    }
    finally
    {
      textBox.setFocus();
    }
  }

  /**
   * Reset result text
   */
  private void resetResultText()
  {
    textResult.setText("");

    lblStatus.setText("Ready");
  }

  /**
   * Print namespaces
   */
  private void printNamespaces()
  {
    Cursor defaultCursor = _shlXmlOperations.getCursor();
    _shlXmlOperations.setCursor(_cursorWait);

    try
    {
      textResult.setForeground(COLOR_BLACK);
      textResult.setText("");

      Xml xml = null;

      try
      {
        xml = new Xml();
        xml.load(textXml.getText());

        ArrayList<String[]> namespaces = xml.getNamespaces();

        if (namespaces.size() == 0)
        {
          textResult.setText("No namespace found");
        }
        else
        {
          StringBuilder sb = new StringBuilder();

          for (String[] pair : namespaces)
          {
            sb.append(String.format("%s = %s\n\n", pair[0], pair[1]));
          }

          textResult.setText(sb.toString().substring(0, sb.length() - 2));
        }
      }
      catch (StringException e)
      {
        printError(FileType.XML, e.getMessage());
        lblStatus.setText(printErrorTime());

        return;
      }
    }
    finally
    {
      setFocus(_lastFocusedFileType);
      _shlXmlOperations.setCursor(defaultCursor);
    }
  }

  /**
   * Search text in the current textbox
   *
   * @param caseSensitive
   *          true if the search is case-sensitive, otherwise false
   */
  private void search(boolean caseSensitive)
  {
    String text = caseSensitive ? textSearch.getText() : textSearch.getText().toLowerCase();

    if (text.length() == 0)
    {
      textSearch.setFocus();
      return;
    }

    String controlText = caseSensitive ? _lastSelectedForClipboard.getText() : _lastSelectedForClipboard.getText().toLowerCase();

    int pos = controlText.indexOf(text, _lastSelectedForClipboard.getSelection().y);
    if (pos == -1)
    {
      _lastSelectedForClipboard.setSelection(0);
      textSearch.setSelection(0, text.length());
      textSearch.setFocus();
    }
    else
    {
      textSearch.setSelection(text.length());
      _lastSelectedForClipboard.setSelection(pos, pos + text.length());
    }
  }

  /**
   * View result content in external browser
   */
  private void viewResultInExternalBrowser()
  {
    if (_defaultBrowserProgram == null)
    {
      return;
    }

    try
    {
      FileContent.writeTextFile(textResult.getText(), _resultFile);
      _defaultBrowserProgram.execute(_resultFile);
    }
    catch (StringException ex)
    {
      SwtMessageBox.messageBoxError(String.format("Unable to write the result temporary file (%s)", ex.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(ex));
    }
  }

  /**
   * Set focus
   *
   * @param fileType
   *          Type of the textbox to set focus on
   */
  public void setFocus(FileType fileType)
  {
    EnhancedStyledText text = null;

    switch (fileType)
    {
      case XML:
        text = textXml;
        break;

      case XPATH:
        text = textXpath;
        break;

      case XSD:
        text = textXsd;
        break;

      case DTD:
        text = textDtd;
        break;

      case XSLT:
        text = textXslt;
        break;

      case XQUERY:
        text = textXquery;
        break;

      default:
        text = textXml;
        break;
    }

    text.setEnabled(false);
    text.setEnabled(true);
    text.setFocus();
  }

  /**
   * Set enabled status of the clipboard and undo-redo buttons
   */
  public void setClipboardAndUndoEnable()
  {
    _display.asyncExec(new Runnable()
    {
      @Override
      public void run()
      {
        boolean isEditable = _lastSelectedForClipboard.getEditable();
        boolean hasSelection = _lastSelectedForClipboard.getSelectionText().length() != 0;

        btnPaste.setEnabled(isEditable);
        btnCopy.setEnabled(hasSelection);
        btnCut.setEnabled(isEditable && hasSelection);

        if (_lastSelectedForClipboardUndoRedo != null)
        {
          btnUndo.setEnabled(isEditable && _lastSelectedForClipboardUndoRedo.hasUndo());
          btnRedo.setEnabled(isEditable && _lastSelectedForClipboardUndoRedo.hasRedo());

          switch (_lastFocusedFileType)
          {
            case DTD:
              btnCopyFolder.setEnabled(_dtdFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy DTD folder path");
              break;

            case XML:
              btnCopyFolder.setEnabled(_xmlFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy XML folder path");
              break;

            case XPATH:
              btnCopyFolder.setEnabled(_xpathFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy XPath folder path");
              break;

            case XSD:
              btnCopyFolder.setEnabled(_xsdFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy XSD folder path");
              break;

            case XSLT:
              btnCopyFolder.setEnabled(_xsltFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy XSLT folder path");
              break;

            case XQUERY:
              btnCopyFolder.setEnabled(_xqueryFile.length() != 0);
              btnCopyFolder.setToolTipText("Copy XQuery folder path");
              break;
          }
        }
        else
        {
          btnUndo.setEnabled(false);
          btnRedo.setEnabled(false);
        }
      }
    });
  }

  /**
   * Set text font
   *
   * @param fontName
   *          Font name
   * @param fontSize
   *          Font size
   * @param fontStyle
   *          Font style
   */
  private void setFont(String fontName, int fontSize, int fontStyle)
  {
    if (fontName == null)
    {
      return;
    }

    Font font = _swtResourceManager.getFont(fontName, fontSize, fontStyle);

    textXml.setFont(font);
    textXpath.setFont(font);
    textXslt.setFont(font);
    textXquery.setFont(font);
    textXsd.setFont(font);
    textDtd.setFont(font);
    textResult.setFont(font);
  }

  private boolean closingXmlFile(ShellEvent e)
  {
    if (_xmlFileChanged)
    {
      switch (SwtMessageBox.messageBox("Do you want to save the current XML changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.XML))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private boolean closingXpathFile(ShellEvent e)
  {
    if (_xpathFileChanged)
    {
      tabsOthers.setSelection(0);
      textXpath.setFocus();

      switch (SwtMessageBox.messageBox("Do you want to save the current XPath changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.XPATH))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private boolean closingXsltFile(ShellEvent e)
  {
    if (_xsltFileChanged)
    {
      tabsOthers.setSelection(1);
      textXslt.setFocus();

      switch (SwtMessageBox.messageBox("Do you want to save the current XSLT changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.XSLT))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private boolean closingXqueryFile(ShellEvent e)
  {
    if (_xqueryFileChanged)
    {
      tabsOthers.setSelection(2);
      textXquery.setFocus();

      switch (SwtMessageBox.messageBox("Do you want to save the current XQuery changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.XQUERY))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private boolean closingXsdFile(ShellEvent e)
  {
    if (_xsdFileChanged)
    {
      tabsOthers.setSelection(3);
      textXsd.setFocus();

      switch (SwtMessageBox.messageBox("Do you want to save the current XSD changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.XSD))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private boolean closingDtdFile(ShellEvent e)
  {
    if (_dtdFileChanged)
    {
      tabsOthers.setSelection(4);
      textDtd.setFocus();

      switch (SwtMessageBox.messageBox("Do you want to save the current DTD changes?", "XML Operations", SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL))
      {
        case SWT.YES:
          if (!fileSave(FileType.DTD))
          {
            e.doit = false;
            return false;
          }
          else
          {
            return true;
          }

        case SWT.NO:
          return true;

        case SWT.CANCEL:
          e.doit = false;
          return false;
      }
    }

    return true;
  }

  private void saveConfiguration()
  {
    try
    {
      FontData fontData = textXml.getFont().getFontData()[0];
      _config.fontName = fontData.getName();
      _config.fontSize = (int) fontData.height;
      _config.fontStyle = (int) fontData.getStyle();

      _config.xmlWrap = textXml.getWordWrap();
      _config.xpathWrap = textXpath.getWordWrap();
      _config.xsltWrap = textXslt.getWordWrap();
      _config.xqueryWrap = textXquery.getWordWrap();
      _config.xsdWrap = textXsd.getWordWrap();
      _config.dtdWrap = textDtd.getWordWrap();
      _config.resultWrap = textResult.getWordWrap();

      FileContent.writeTextFile(_gson.toJson(_config) + "\r\n", Platform.CURRENT_DIRECTORY + "xmloperations.json");
    }
    catch (StringException ex)
    {
      SwtMessageBox.messageBoxError(String.format("Unable to save the configuration file (%s)", ex.getMessage()), "XML Operations");
      _log.error(Run.getExceptionStackTrace(ex));
    }
  }

  /**
   * Print last operation duration
   *
   * @param operation
   *          Operation to print information relative to
   * @param startTime
   *          Operation start moment
   *
   * @return Friendly text of the operation duration
   */
  private static String printTimeDifference(String operation, long startTime)
  {
    return String.format("%s duration: %s", operation, TimeMeasure.printTimeDifference(startTime));
  }

  /**
   * Print error time
   *
   * @return Printed error time
   */
  private static String printErrorTime()
  {
    return String.format("Error time: %tT", System.currentTimeMillis());
  }

  /**
   * Split sash form to right or down on double-click
   *
   * @param sashForm
   *          SashForm to be split
   */
  private static void splitToRightOrDownOnDoubleClick(SashForm sashForm)
  {
    int[] weights = sashForm.getWeights();

    if (weights[0] < weights[1])
    {
      sashForm.setWeights(new int[] { 1, 1 });
    }
    else
    {
      sashForm.setWeights(new int[] { 10, 1 });
    }
  }

  /**
   * Split sash form to left or up on double-click
   *
   * @param sashForm
   *          SashForm to be split
   */
  private static void splitToLeftOrUpOnDoubleClick(SashForm sashForm)
  {
    int[] weights = sashForm.getWeights();

    if (weights[0] > weights[1])
    {
      sashForm.setWeights(new int[] { 1, 1 });
    }
    else
    {
      sashForm.setWeights(new int[] { 1, 10 });
    }
  }
}
