package com.amazon.kinesis.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

public class FirehoseSinkConnector extends SinkConnector {

	public static final String DELIVERY_STREAM = "deliveryStream";

	public static final String BATCH = "batch";
	
	public static final String BATCH_SIZE = "batchSize";
	
	public static final String BATCH_SIZE_IN_BYTES = "batchSizeInBytes";

	public static final String REGION = "region";

	public static final String AWS_ACCESS_KEY = "accessKey";

	public static final String AWS_SECRET_KEY = "secretKey";
	
	private String deliveryStream;
	
	private String batch;
	
	private String batchSize;
	
	private String batchSizeInBytes;

	private String region;

	private String accessKey;

	private String secretKey;

	private final String MAX_BATCH_SIZE = "500";
	
	private final String MAX_BATCH_SIZE_IN_BYTES = "3670016";

	@Override
	public void start(Map<String, String> props) {
		deliveryStream = props.get(DELIVERY_STREAM);
		region = props.get(REGION);
		batch = props.get(BATCH);	
		batchSize = props.get(BATCH_SIZE);
		batchSizeInBytes = props.get(BATCH_SIZE_IN_BYTES);
		accessKey = props.get(AWS_ACCESS_KEY);
		secretKey = props.get(AWS_SECRET_KEY);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<? extends Task> taskClass() {
		return FirehoseSinkTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		ArrayList<Map<String, String>> configs = new ArrayList<>();
		for (int i = 0; i < maxTasks; i++) {
			Map<String, String> config = new HashMap<>();
			if (deliveryStream != null)
				config.put(DELIVERY_STREAM, deliveryStream);
			
			if(region != null)
				config.put(REGION, region);

			if (accessKey != null)
				config.put(AWS_ACCESS_KEY, accessKey);

			if (secretKey != null)
				config.put(AWS_SECRET_KEY, secretKey);
			
			if(batch != null)
				config.put(BATCH, batch);
			
			if(batchSize != null)
				config.put(BATCH_SIZE, batchSize);
			else
				config.put(BATCH_SIZE, MAX_BATCH_SIZE);
			
			if(batchSizeInBytes != null)
				config.put(BATCH_SIZE_IN_BYTES,  batchSizeInBytes);
			else 
				config.put(BATCH_SIZE_IN_BYTES, MAX_BATCH_SIZE_IN_BYTES);
			
			configs.add(config);
		}
		return configs;
	}

	@Override
	public String version() {
		// Currently using Kafka version, in future release use Kinesis-Kafka version
		return AppInfoParser.getVersion();
	}

	@Override
	public ConfigDef config() {
		return new ConfigDef();
	}

}
