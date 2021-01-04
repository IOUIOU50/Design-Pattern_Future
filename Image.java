import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* 예시코드 : 이미지를 url에서 가져오는 동안, 이미지와 프레임을 처리하는 작업 수행
 * 1. 가장 먼저 Future, 즉 교환권을 받아온다
 * 2. Future는 데이터 처리를 ExecutorService의 ThreadPool에 위임
 * 3. ThreadPool에서 열심히 이미지를 url로부터 가져오고 있는 것과 동시에
 *    이 이미지가 구성될 프레임을 조직하는 작업을 같이 수행
 * 4. 마지막으로 구성 작업이 끝나면 이미지를 구성이 끝난 프레임에 부착*/
public class Image extends JFrame{
	public Image() throws InterruptedException, ExecutionException {
		
		/* Future pattern을 Java에서 사용하기 위해 
		 * 1. ExecutorService
		 * 2. Future
		 * 를 이용한다.
		 * 먼저 ExecutorService를 통해 스레드풀을 생성하고
		 * 작업이 처리될 Future를 등록*/
		ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<ImageIcon> futureTask1 = executor.submit(() -> {
            ImageIcon image = new ImageIcon();
            URL url = null;
            try {
                url = new URL("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTA0MjlfOTMg%2FMDAxNTU2NDY1MjU5Mzc1.Yej0KRt0XNCgmBiRIuBAQiflte86UDPx8zgZ2x61hb0g.8TJiRypjCQqIVOol2-Fq58ymCvKph_wmm2kefDqUcScg.JPEG.badpark%2F54.jpg&type=sc960_832");
                image.setImage(ImageIO.read(url));
            } catch (MalformedURLException ex) {
                System.out.println("Malformed URL");
            } catch (IOException iox) {
                System.out.println("Can not load file");
            }
        	return image;
        });
		//여기까지 작업 처리를 요청하고, future교환권을 얻어옴
        
        //여기서부터 요청한 작업과 동시에 이미지가 들어갈 프레임을 조직
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
        panel.add(label, BorderLayout.CENTER);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        //이 외에도 추가적으로 필요한 작업등을 수행할 수 있음
        

        /* 클라이언트에서 처리해야 할 이미지를 부착할 사전작업을 모두 끝낸 뒤,
         * 이제 이미지만 붙여넣으면 끝날 때
         * future로부터 이미지를 가져옴*/
        label.setIcon(futureTask1.get());
        this.pack();
        this.setVisible(true);
	}
}
