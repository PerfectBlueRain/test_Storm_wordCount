import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import java.util.Map;

/*문장 생성 (1)*/
public class SentenceSpout extends BaseRichSpout {  // BaseRichSpout를 상속받아야 Spout로사용

    private SpoutOutputCollector collector;  // SpoutOutputCollector로 Bolt에게 넘겨줌
    private int index = 0;
    
    private String[] sentences = { //임의로 데이터를 생성 
        "my dog has fleas",
        "i like cold beverages",
        "the dog ate my homework",
        "don't have a cow man",
        "i don't think i like fleas"
    };

    // open() : 해당 spout가 초기화될때 호출됨
    public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    // declareOutputFields() : 이클래스에서 제공되는 Stream을 이용할때 key값을 Event를 제공받는다.
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence")); // key -"sentence"라는 이벤트채널에 계속해서 넘겨주게된다
    }

    // nextTuple() : 핵심! SpoutOutputCollector를 통해 Stream을 내보내라고 요청!
    public void nextTuple() {
        this.collector.emit(new Values(sentences[index])); //key-value형태로 넘겨지기때문에
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        Utils.waitForMillis(1); // 1초마다 문자열이 넘어간다.
    }
}
