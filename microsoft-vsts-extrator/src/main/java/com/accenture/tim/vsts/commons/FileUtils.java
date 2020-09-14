package com.accenture.tim.vsts.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.DirectoryScanner;

public class FileUtils {

	final static Logger log  = Logger.getLogger(FileUtils.class);
	
	public static String name2CSVFile(String pattern) {
		String ret="";
		
		log.debug("- name2CSVFile("+pattern+")");
		
		if ( pattern==null || "".equals(pattern.trim()) )
			pattern="falta_pattern_";
		
		try {
			ret = pattern + DateUtils.date2CSVFileName()+".csv";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("##ERROR ### ["+pattern+"]#",e);
		}	
		return ret;
	}
	
	
	public static List<File> ltFilesByPattern4Today(String folderName) {
		
		String pattern4Today="pattern4Today" + DateUtils.date2CSVFileName();
		
		String[] 
		arrFilter   = new String[1];
		arrFilter[0]="**/*_"+pattern4Today+".csv";

		DirectoryScanner 
		scanner = new DirectoryScanner();
		scanner.setIncludes(arrFilter);
		scanner.setBasedir(folderName);
		scanner.setCaseSensitive(false);
		scanner.scan();
		
		String[] listOffileNames = scanner.getIncludedFiles();
		List<File> listOffiles  = new ArrayList<File>(listOffileNames.length); 
		for (String fn : listOffileNames) {
			listOffiles.add( new File(fn));
		}
		return listOffiles;
		
	}

	public static List<File> listFiles(String folderName) {
		return listFiles(new File(folderName) );
	}
	
	
	public static List<File> listFiles(File folder) {
		return 	listFiles(folder, null);
	}

	public static List<File> listFiles(File folder, String pattern) {
		

		File[] listOfFiles = folder.listFiles();
		
		String fname = "";

		pattern=pattern + DateUtils.date2CSVFileName();
		
		for (File fileEntry : folder.listFiles()) {
			
			if (fileEntry.isFile()) {
				fname = fileEntry.getName();
				
				System.out.println(fname);
			}
	    }
		return Arrays.asList(listOfFiles);
	}


	public static List<File> getFiles2CompareToday(String folderDomain, String patternDomain, String folderContradomain, String patternContradomain) throws Exception {
		File fdomain = new File(folderDomain);
		File fcontra = new File(folderContradomain);
		return 	getFiles2CompareToday(fdomain, patternDomain, fcontra, patternContradomain);

	}
	/**
	 * 
	 * @param folderDomain
	 * @param patternDomain
	 * @param folderContradomain
	 * @param patternContradomain
	 * @return
	 */
	public static List<File> getFiles2CompareToday(File folderDomain, String patternDomain, File folderContradomain, String patternContradomain) throws Exception{
		
		if (folderDomain      ==null || folderDomain      .listFiles()==null || 
			folderContradomain==null || folderContradomain.listFiles()==null)
			return null;
		
		
		String	fileName4Domain = name2CSVFile(patternDomain);
		String	nameFileDoman	= null;
		File	fileDomain    	= null;
		for (File fileEntry : folderDomain.listFiles()) {
			nameFileDoman=fileEntry.getName().trim();
			fileDomain=fileEntry;
			if ( fileEntry.isFile() && nameFileDoman.equals(fileName4Domain.trim()) ) {
				break;
			}else {
				nameFileDoman=null;
				fileDomain=null;
			}
	    }
		log.info("- nameFileDoman....=["+nameFileDoman+"]");
		
		
		String	fileName4ContraDomain= name2CSVFile(patternContradomain);//patternContradomain+ DateUtils.date2CSVFileName()+".csv";
		String	nameFileContrdoman	 = null;
		File	fileContradomain	 = null;
		for (File fileEntry : folderContradomain.listFiles()) {
			nameFileContrdoman=fileEntry.getName().trim();
			fileContradomain =fileEntry; 
			if ( fileEntry.isFile() && nameFileContrdoman.equals(fileName4ContraDomain.trim()) ) {
				break;
			}else {
				nameFileContrdoman=null;
				fileContradomain=null;
			}
	    }
		log.info("- nameFileContrdoman.....=["+nameFileContrdoman+"]");
		File[] ret = {fileDomain,fileContradomain};
		return Arrays.asList(ret);
	}
	
	
	
	public static void purgeTodaysFiles(String dir) {

		log.info("-> purgeTodaysFiles()");
		log.info("-> dir=["+dir+"]");
		
        try {

			String	filterToDay	= DateUtils.date2CSVFileName();
			log.info("-> filterToDay=["+filterToDay+"]");
		
			Path	filePath	= Paths.get(dir);
			File 	fileDir		= filePath.toFile();		
	        File[]	fileList	= fileDir.listFiles();
        
	        if (fileList!=null)
		        for(File file : fileList){
		        	if ( !file.isDirectory() && file.getName().indexOf(filterToDay)>0 ) {
	                    if(file.delete()){
	                       log.info("Deleted = [" + file.getCanonicalPath() + "]");
	                    }else{
	                        log.info("Could not be deleted = [" + file.getCanonicalPath() + "]");
	                    }
		        	}
		        }
        } catch (IOException e) {
            e.printStackTrace();
        }
		log.info("-> purgeTodaysFiles()");
	}

	
	public static void purge(String dir, String filter) {
		log.info("-> purge("+dir+filter+")");
		
		
		
	}
	

	public static void append(File[] ltFilesToAppend, File appendFile) {

		FileWriter		outFileWriter	= null;
		BufferedWriter	outBuffWriter	= null;
		
		BufferedReader 	inBuffReader 	= null;
		FileInputStream inFileStream	= null;
		String			inLine			= null;

		int countFile=0;
		int countLine=1;

		
		try {
		
			outFileWriter	= new FileWriter(appendFile, true);
			outBuffWriter 	= new BufferedWriter(outFileWriter);

			// Apartir do 2o arquivo da lista 'filesToAppend', a primeira linha é ignorada 
			// Só pode ler a primeira linha (header) do primeiro arquivo.
			for (File fileAppend : ltFilesToAppend) {
			
				log.info("-  Appending["+countFile+"] = ["+fileAppend.getName()+"]");
				countFile++;
				
				inFileStream = new FileInputStream(fileAppend);
				inBuffReader = new BufferedReader(new InputStreamReader(inFileStream));
					
				countLine=1; // Primeira linha do arquivo fileAppend
				while ( (inLine = inBuffReader.readLine() ) != null) {
					if (countFile==1) {
						outBuffWriter.write(inLine);
						outBuffWriter.newLine();
					}else {
						if ( countLine>1) {
							outBuffWriter.write(inLine);
							outBuffWriter.newLine();
						}
					}
					countLine++;	
				}
				countLine=0;
				inBuffReader.close();
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally {
			try {
				outBuffWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e);
			}		
		}
		
		

		
	}


	public static void mergeFiles(String dirFilesToMerge, String dirToWriteMergedFile, String fileNameMerged) {

		try {
			log.debug("-> mergeFiles");

			String newFile = dirToWriteMergedFile + fileNameMerged;
			log.debug("- newFile=["+newFile +"]");
			
			log.debug("-  Creating New File");
			File 
			myFile = new File(newFile);
			myFile.createNewFile();

			log.debug("-  To Write on New File");
			PrintWriter pw = new PrintWriter(newFile);
	
			// To List of files os Specific Directory
			myFile = new File(dirFilesToMerge);
			String[] list = myFile.list();
	
			for(String s:list) {
				
				File f = new File(dirFilesToMerge + s);
				
				if(f.isFile()) {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = br.readLine();
					while(line != null) {
						pw.println(line);
						line = br.readLine();
					}
					br.close();
				}
			}
			pw.flush();
			pw.close();

			log.debug("<-mergeFiles");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	
	
	
//////////////////////////////
	

	public static void main(String[] args) throws Exception {

		//FileUtils.listFiles( "diff/"); 
		
//		List<File> f;
//		try {
//			f = FileUtils.getFiles2CompareToday("diff/", "REPOEPICDIFF_", "csv/", "REPOEPICDEV_");
//			for (File file : f) 
//				System.out.println(file);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(name2CSVFile("mario_"));

		
//		String dirFilesToMerge		= "git/tmp/";
//		String dirToWriteMergedFile = "git/";
//		String fileNameMerged		= name2CSVFile("REPO_BRANCH_"); 
//		FileUtils.mergeFiles(dirFilesToMerge, dirToWriteMergedFile, fileNameMerged); 
	
		//FileUtils.purgeTodaysFiles("csv/");
		
/*	
		log.info("========================================================================");
		log.info("-  Append 'Bugs'(Workitens) into 'Epicos'(Workitens) ...");
		log.info("========================================================================");
		Properties 
		prop = new Properties();
		prop.load(SendEmail.class.getResourceAsStream("/application.properties"));

		String	epicfilePattern	= prop.getProperty("query._epicos.branchs_.file.pattern"); // REPO_EPIC_
		String	epicfileDir   	= prop.getProperty("query._epicos.branchs_.dir"); // csv
		String  epicFileName	= FileUtils.name2CSVFile(epicfilePattern); // REPO_EPIC_20180615.csv
		Path	epicFilePath	= Paths.get( epicfileDir, epicFileName);
		File 	epicFile		= epicFilePath.toFile();
			
		String	bugfilePattern	= prop.getProperty("query._bugs_.file.pattern"); // REPO_BUGS_
		String	bugfileDir   	= prop.getProperty("query._bugs_.dir"); // csv
		String  bugFileName		= FileUtils.name2CSVFile(bugfilePattern); // REPO_BUGS_20180615.csv
		Path	bugFilePath		= Paths.get( bugfileDir, bugFileName);
		File 	bugFile			= bugFilePath.toFile();
	
		String	appendFilePattern= prop.getProperty("task._epicos.branchs_.contradomain.file.pattern"); // REPO_EPIC_BUG_
		String	appendFileDir   =  prop.getProperty("task._epicos.branchs_.contradomain.dir"); // diff/
		String 	appendFileName	=  FileUtils.name2CSVFile(appendFilePattern); // REPO_EPIC_BUG_20180615.csv
		Path	appendFilePath	=  Paths.get( appendFileDir, appendFileName);
		File 	appendFile		=  appendFilePath.toFile();

		File[]  filesToAppend	= {epicFile,bugFile};
		FileUtils.append(filesToAppend, appendFile); // Creates REPO_EPIC_BUG_20180615.csv file to compare with 		
*/

		purgeTodaysFiles("csv/");
		purgeTodaysFiles("csv/");
		
		
	}




}
