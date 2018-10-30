import com.amazonaws.services.kinesis.producer.*;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KinesisProducerTester {

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("Enter the arguments expected by the test client, as below.\n");
                System.out.println("java -cp kinesis-client.jar KinesisProducer [Stream Name] [Path To Payload] \n");
            }
            final String streamName = args[0];
            final String pathToPayload = args[1];

            com.amazonaws.services.kinesis.producer.KinesisProducer kinesis = new com.amazonaws.services.kinesis.producer.KinesisProducer(
                    new KinesisProducerConfiguration()
                            .setRegion("ap-southeast-2"));
            List<Future<UserRecordResult>> putFutures = new LinkedList<>();
            for (int i = 0; i < 1; i++) {
                ByteBuffer data = ByteBuffer.wrap("{\"Timestamp\":\"1535241597\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":152.7743,\"29\":0,\"30\":288014.9,\"57610\":242.6487,\"57628\":0.2841492,\"57646\":0,\"57649\":0,\"57655\":-0.8437307,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":92.7998,\"29\":0,\"30\":-182631.9,\"57610\":242.6226,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-90.64236,\"29\":0,\"30\":-1580.319,\"57610\":242.4847,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}|{\"Timestamp\":\"1535241602\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":153.2358,\"29\":0,\"30\":288014.9,\"57610\":242.3508,\"57628\":0.2845459,\"57646\":0,\"57649\":0,\"57655\":-0.8435161,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":89.78011,\"29\":0,\"30\":-182631.9,\"57610\":242.3256,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-94.59984,\"29\":0,\"30\":-1580.319,\"57610\":242.1887,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}|{\"Timestamp\":\"1535241607\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":154.303,\"29\":0,\"30\":288014.9,\"57610\":242.1091,\"57628\":0.2892761,\"57646\":0,\"57649\":0,\"57655\":-0.8451053,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":92.60822,\"29\":0,\"30\":-182631.9,\"57610\":242.0853,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-93.77449,\"29\":0,\"30\":-1580.319,\"57610\":241.9478,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}".getBytes("UTF-8"));
                // doesn't block
            putFutures.add(
                    kinesis.addUserRecord("MondoTest-ap-southeast-2-MondoEventData-9K6LORY8PX64", "myPartitionKey", data));
//                putFutures.add(
//                        kinesis.addUserRecord("events", "myPartitionKey", data));
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
