import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {

	// �� component�� ID����
    private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main(String[] args) throws Exception {
    	
    	// ���Ǵ� Component����
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        /*--------------------��������----------------------*/
        // SentenceSpout --> SplitSentenceBolt --> WordCountBolt --> ReportBolt
        TopologyBuilder builder = new TopologyBuilder();
        
        /* ���� ���� (1) */
        builder.setSpout(SENTENCE_SPOUT_ID, spout, 3); // Spout�� 3����! 
        /* ���� ������(2) */
        builder.setBolt(SPLIT_BOLT_ID, splitBolt)
               .shuffleGrouping(SENTENCE_SPOUT_ID); //���ñ׷���
        /* �ܾ��(3) */
        builder.setBolt(COUNT_BOLT_ID, countBolt)
        	   .fieldsGrouping(SPLIT_BOLT_ID, new Fields("word")); // �ʵ�׷���
        /* ������(4) */
        builder.setBolt(REPORT_BOLT_ID, reportBolt)
        	   .globalGrouping(COUNT_BOLT_ID);		// �۷ι��׷��� 

        
        /*--------------------Cluster ����----------------------*/
        Config config = new Config();
        LocalCluster cluster = new LocalCluster(); //Local Cluster�α���
        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        Utils.waitForSeconds(10);//10�ʵڿ�
        cluster.killTopology(TOPOLOGY_NAME);// ������ ���������� ���� storm kill
        cluster.shutdown();
    }
}
