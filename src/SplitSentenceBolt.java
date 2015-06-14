import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import java.util.Map;

/* ���� ������(2) */
public class SplitSentenceBolt extends BaseRichBolt{  //BaseRichBolt
    private OutputCollector collector; // ������ ó���� ������ �Ѱ��ֱ�����

    public void prepare(Map config, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }
    
    // execute() : ����ó���۾��� �����ϴ� �Լ�
    public void execute(Tuple tuple) {
        String sentence = tuple.getStringByField("sentence");// tuple(�Ѱ��ص�����)���� key�� value�� ����
        String[] words = sentence.split(" ");
        for(String word : words){
            this.collector.emit(new Values(word)); // OutputCollector�� emit()�� �������� ������(1)
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));  // ������ �Ѱ��� Fields�� key -"word"�� �������ش�!(2)
    }
}
