import com.amazonaws.services.kinesis.producer.Attempt;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;
import com.amazonaws.services.kinesis.producer.UserRecordResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

public class KinesisProducer {

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("Enter the arguments expected by the test client, as below.\n");
                System.out.println("java -cp kinesis-client.jar KinesisProducer [Stream Name] [Path To Payload] \n");
                System.exit(-1);
            }
            final String streamName = args[0];
            final String pathToPayload = args[1];

            com.amazonaws.services.kinesis.producer.KinesisProducer kinesis = new com.amazonaws.services.kinesis.producer.KinesisProducer(
                    new KinesisProducerConfiguration()
                            .setRegion("ap-southeast-2"));
            List<Future<UserRecordResult>> putFutures = new LinkedList<>();
            for (int i = 0; i < 1; i++) {
                File file = new File(pathToPayload);
                if (!file.exists()) {
                    System.out.println("File '" + pathToPayload + "' does not exist.");
                    System.exit(-1);
                }
                byte[] encoded = Files.readAllBytes(Paths.get(pathToPayload));
                final String payload = new String(encoded, "UTF-8");
                ByteBuffer data = ByteBuffer.wrap(payload.getBytes("UTF-8"));
                putFutures.add(kinesis.addUserRecord(streamName, "myPartitionKey", data));
            }

            // Wait for puts to finish and check the results
            for (Future<UserRecordResult> f : putFutures) {
                UserRecordResult result = f.get(); // this does block
                if (result.isSuccessful()) {
                    System.out.println("Put record into shard " +
                            result.getShardId());
                } else {
                    for (Attempt attempt : result.getAttempts()) {
                        // Analyze and respond to the failure
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            UserRecordFailedException ufe = (UserRecordFailedException) e.getCause();
            System.out.println(ufe.getResult());
        }
    }

}
