import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	public static void main(String[] args) {

		try {
			System.out.println("-> Main");
			
			
			//mongodb+srv://admin:<PASSWORD>@cluster0-rv6nl.mongodb.net/test?retryWrites=true
			
			//mongodb://admin:<PASSWORD>@cluster0-shard-00-00-rv6nl.mongodb.net:27017,
			//                           cluster0-shard-00-01-rv6nl.mongodb.net:27017,
			//                           cluster0-shard-00-02-rv6nl.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true

			String url = "mongodb://admin:admin@cluster0-shard-00-00-rv6nl.mongodb.net:27017,"
                                             + "cluster0-shard-00-01-rv6nl.mongodb.net:27017,"
                                             + "cluster0-shard-00-02-rv6nl.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
			
			MongoClientURI		uri = new MongoClientURI(url);
			MongoClient			mongoClient = new MongoClient(uri);
			MongoDatabase 		database	= mongoClient.getDatabase("tonapraia");

			MongoCollection<?>		consumidor = database.getCollection("consumidor");
			MongoCollection<?>		vendedor   = database.getCollection("vendedor");
			
			@SuppressWarnings("deprecation")
			long qtdConsumidor = consumidor.count();
			@SuppressWarnings("deprecation")
			long qtdVendedor   = vendedor.count();
			
			System.out.println("consumidor=["+qtdConsumidor+"]");
			System.out.println("vendedor=["+qtdVendedor+"]");
			
			mongoClient.close();
			
			System.out.println("<- Main");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
