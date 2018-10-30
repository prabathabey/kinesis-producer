import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadTester {

    public static void main(String[] args) {
        try {
            ExecutorService service = Executors.newFixedThreadPool(50);

            KinesisProducer kinesis =
                    new KinesisProducer(new KinesisProducerConfiguration().setRegion("ap-southeast-2"));
            int counter = 0;
            while (true) {
//                for (int i = 0; i < 512; i++) {
                counter++;
                    service.submit(() -> {
                        try {
                            ByteBuffer buff = ByteBuffer.wrap("{\"Timestamp\":\"1535241597\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":152.7743,\"29\":0,\"30\":288014.9,\"57610\":242.6487,\"57628\":0.2841492,\"57646\":0,\"57649\":0,\"57655\":-0.8437307,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":92.7998,\"29\":0,\"30\":-182631.9,\"57610\":242.6226,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-90.64236,\"29\":0,\"30\":-1580.319,\"57610\":242.4847,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}|{\"Timestamp\":\"1535241602\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":153.2358,\"29\":0,\"30\":288014.9,\"57610\":242.3508,\"57628\":0.2845459,\"57646\":0,\"57649\":0,\"57655\":-0.8435161,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":89.78011,\"29\":0,\"30\":-182631.9,\"57610\":242.3256,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-94.59984,\"29\":0,\"30\":-1580.319,\"57610\":242.1887,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}|{\"Timestamp\":\"1535241607\",\"DeviceID\":\"DEV0000001618\",\"EndPoints\":{\"1\":{\"28\":154.303,\"29\":0,\"30\":288014.9,\"57610\":242.1091,\"57628\":0.2892761,\"57646\":0,\"57649\":0,\"57655\":-0.8451053,\"57664\":392518.4,\"57721\":104503.6},\"2\":{\"28\":92.60822,\"29\":0,\"30\":-182631.9,\"57610\":242.0853,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":182631.9},\"3\":{\"28\":-93.77449,\"29\":0,\"30\":-1580.319,\"57610\":241.9478,\"57628\":0,\"57646\":0,\"57649\":0,\"57655\":1,\"57664\":0,\"57721\":1580.319}}}".getBytes("UTF-8"));
                            kinesis.addUserRecord("events", "myPartitionKey", buff);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });
//                }
                System.out.println(counter);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            UserRecordFailedException ufe = (UserRecordFailedException) e.getCause();
            System.out.println(ufe.getResult());
        }
    }
}
