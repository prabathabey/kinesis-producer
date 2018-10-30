import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class InfluxReader {

    public static void main(String[] args) {
//        InfluxDB client = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin@123");
        InfluxDB client = InfluxDBFactory.connect("http://172.17.92.242:8086", "admin", "^QWP)FEtYIETGqn");
        Pong pong = client.ping();
        if (pong != null) {
            System.out.println("Success!");
        }

        try {
            final Query query = new Query("SELECT count(*) FROM rawData WHERE time > now() - 1680h", "mondotest");
            client.query(query, queryResult -> {
                System.out.println(queryResult.getResults());
                }, throwable ->
                    throwable.printStackTrace());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
