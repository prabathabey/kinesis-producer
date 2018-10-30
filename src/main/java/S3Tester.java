import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class S3Tester {

    private String bucketName;
    private String region;
    private AmazonS3 client;

    public S3Tester(String bucketName, String region) {
        this.bucketName = bucketName;
        this.region = region;

        this.client = AmazonS3ClientBuilder.standard()
                .withRegion(this.region)
                .build();
    }

    public void addData(String key) throws IOException {
        this.client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
    }

    private File createSampleFile() throws IOException {
        OutputStreamWriter writer = null;
        File file;
        try {
            file = File.createTempFile("test-file", ".txt");
            file.deleteOnExit();
            writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write("dggfgfgfgf");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return file;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public String getRegion() {
        return this.region;
    }

    public static void main(String[] args) {
        S3Tester tester = new S3Tester("2871-test-bucket-pa1", "ap-southeast-2");
        try {
            tester.addData("ttt");
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
