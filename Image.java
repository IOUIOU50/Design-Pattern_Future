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

/* �����ڵ� : �̹����� url���� �������� ����, �̹����� �������� ó���ϴ� �۾� ����
 * 1. ���� ���� Future, �� ��ȯ���� �޾ƿ´�
 * 2. Future�� ������ ó���� ExecutorService�� ThreadPool�� ����
 * 3. ThreadPool���� ������ �̹����� url�κ��� �������� �ִ� �Ͱ� ���ÿ�
 *    �� �̹����� ������ �������� �����ϴ� �۾��� ���� ����
 * 4. ���������� ���� �۾��� ������ �̹����� ������ ���� �����ӿ� ����*/
public class Image extends JFrame{
	public Image() throws InterruptedException, ExecutionException {
		
		/* Future pattern�� Java���� ����ϱ� ���� 
		 * 1. ExecutorService
		 * 2. Future
		 * �� �̿��Ѵ�.
		 * ���� ExecutorService�� ���� ������Ǯ�� �����ϰ�
		 * �۾��� ó���� Future�� ���*/
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
		//������� �۾� ó���� ��û�ϰ�, future��ȯ���� ����
        
        //���⼭���� ��û�� �۾��� ���ÿ� �̹����� �� �������� ����
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
        panel.add(label, BorderLayout.CENTER);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        //�� �ܿ��� �߰������� �ʿ��� �۾����� ������ �� ����
        

        /* Ŭ���̾�Ʈ���� ó���ؾ� �� �̹����� ������ �����۾��� ��� ���� ��,
         * ���� �̹����� �ٿ������� ���� ��
         * future�κ��� �̹����� ������*/
        label.setIcon(futureTask1.get());
        this.pack();
        this.setVisible(true);
	}
}
