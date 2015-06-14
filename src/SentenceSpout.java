import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import java.util.Map;

/*���� ���� (1)*/
public class SentenceSpout extends BaseRichSpout {  // BaseRichSpout�� ��ӹ޾ƾ� Spout�λ��

    private SpoutOutputCollector collector;  // SpoutOutputCollector�� Bolt���� �Ѱ���
    private int index = 0;
    
    private String[] sentences = { //���Ƿ� �����͸� ���� 
        "my dog has fleas",
        "i like cold beverages",
        "the dog ate my homework",
        "don't have a cow man",
        "i don't think i like fleas"
    };

    // open() : �ش� spout�� �ʱ�ȭ�ɶ� ȣ���
    public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    // declareOutputFields() : ��Ŭ�������� �����Ǵ� Stream�� �̿��Ҷ� key���� Event�� �����޴´�.
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence")); // key -"sentence"��� �̺�Ʈä�ο� ����ؼ� �Ѱ��ְԵȴ�
    }

    // nextTuple() : �ٽ�! SpoutOutputCollector�� ���� Stream�� ��������� ��û!
    public void nextTuple() {
        this.collector.emit(new Values(sentences[index])); //key-value���·� �Ѱ����⶧����
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        Utils.waitForMillis(1); // 1�ʸ��� ���ڿ��� �Ѿ��.
    }
}
