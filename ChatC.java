
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatC extends Thread{

	public static String HOST;
	public static int PORT;
	private static Socket sc;
	private static  BufferedReader br;
	private static  PrintWriter pw;
	private static  BufferedReader mes;
	static String name;

	public static void main(String[] args) {
		try{
			mes = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("�z�X�g������͂��Ă�������");
			HOST = mes.readLine();

			System.out.println("�|�[�g�ԍ�����͂��Ă�������");
			PORT = Integer.parseInt(mes.readLine());

			System.out.println("����ɕ\�����閼�O����͂��Ă�������");
			name = mes.readLine();

			//�ڑ�
			sc = new Socket(HOST,PORT);
			System.out.println("�ڑ����܂���");
			//����M����
			//��M
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			//���M
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
			//����M
			while(true){
				try{

					ChatC cc = new ChatC();
					cc.start();

					String you = br.readLine();
					System.out.println(you);

				}catch (Exception e) {

					br.close();
					pw.close();
					mes.close();
					sc.close();

					System.out.println("�ؒf����܂���");

					System.exit(0);

				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void run(){
		while(true){
			try{

				String me = mes.readLine();

				pw.println(name+">"+me);

				pw.flush();

			}catch (Exception e) {
				try{

					br.close();
					pw.close();
					mes.close();
					sc.close();

					System.out.println("�ؒf����܂���");

					System.exit(0);

				}catch (Exception ex) {
					e.printStackTrace();
				}
			}
		}
	}
}