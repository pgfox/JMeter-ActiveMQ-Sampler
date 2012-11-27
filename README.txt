NOTE: This is a work in progress 


A good overview description of the various components that make up a Jmeter test plan is available 
here http://jmeter.apache.org/usermanual/test_plan.html. The “Samplers” tell JMeter to send 
requests to a server and wait for a response.

There also seems to be some JMeter code here http://activemq.apache.org/jmeter-performance-tests.html. 


JMeter  has a JMS sampler already – http://jmeter.apache.org/usermanual/component_reference.html#JMS_Publisher. 
You can also write your own sample that publishes messages to AMQ. The com.acme.AMQProducerQueueSampler is a
very simple sampler that creates a JMSProducer and sends messages.
 
The java custom sampler must implement org.apache.jmeter.protocol.java.sampler.JavaSamplerClient. 
Custom Java samplers can be added to a test plan by right clicking on the threadgroup -> "Sampler" -> "Java Sampler"
On that panel you can select the appropriate class name and set appropriate parameters for the sampler class.

The properties used by com.acme.AMQProducerQueueSampler are set by default and displayed in the "java sampler" panel.
Theses properties values can by modified within the "java sampler" panel.

For ease of use, I have created a sample Test Plan called "AMQProducerTest.jmx" (located in the root directory).



How to install/run this example com.acme.AMQProducerQueueSampler Sampler?
=========================================================================

1. Download JMeter from http://jmeter.apache.org/download_jmeter.cgi

2. mvn install

    builds the target/JMeter-ActiveMQ-Sampler-1.0-SNAPSHOT.jar 

3. Copy JMeter-ActiveMQ-Sampler-1.0-SNAPSHOT.jar to apache-jmeter-2.8/lib/ext/

4. Copy activemq-all-5.5.1-fuse-08-15.jar (found in the bin Fuse message broker install 
/apache-activemq-5.5.1-fuse-08-15 directory) into  apache-jmeter-2.8/lib/ext/

5.  Copy the JMeterPlugins.jar (found in JMeterPlugins-0.5.5.zip downloaded from http://code.google.com/p/jmeter-plugins/ )
into apache-jmeter-2.8/lib/ext/ - this will give you some extra components for graphing etc.

6.  Start JMeter using the ./jmeter and open the test plan named AMQProducerTest.jmx (located in this directory) .

7. Use the “Run” -> “Start” menu to run the Test Plan.

8. View the graphed result by clicking on the “jp@gcTransaction per Second”