
import java.io.*;
import java.net.*;

public class ChatS {
	//�|�[�g�ԍ�
	public static int PORT;

	public static void main(String[] args) {
		//�|�[�g�ԍ��̎w��
		try{
			BufferedReader key = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("�|�[�g�ԍ�����͂��Ă�������");
			PORT=Integer.parseInt(key.readLine());

			System.out.println("����ɕ\�����閼�O����͂��Ă�������");
			String name = key.readLine();

			ChatS cs = new ChatS();
			//�ڑ�
			ServerSocket ss = new ServerSocket(PORT);

			System.out.println("�ҋ@���܂�");

			while(true){
				try{
					Socket sc = ss.accept();

					System.out.println("�ڑ����܂���");
					//�X���b�h�N��
					Client cl = new Client(sc,name);
					cl.start();
				}catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
//����Ƃ̏���
class Client extends Thread {

	private Socket sc;
	private BufferedReader br;
	private PrintWriter pw;
	private BufferedReader mes;
	private String name;

	public Client(Socket s, String n){
		sc = s;
		name = n;
	}

	public void run(){
		//����M����
		try{
			//��M
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			//���M
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
			//����
			mes = new BufferedReader(new InputStreamReader(System.in));

		}catch(Exception e){
			e.printStackTrace();
		}

		//����M
		while(true){

			try{

				String you = br.readLine();
				System.out.println(you);

				Sousin sousin = new Sousin(br,pw,mes,sc,name);
				sousin.start();

			}catch (Exception e) {
				try{

					br.close();
					pw.close();
					sc.close();
					mes.close();

					System.out.println("�ؒf����܂���");

					System.exit(0);

				}catch (Exception ex) {
					e.printStackTrace();
				}
			}
		}
	}
}
class Sousin extends Thread {

	private Socket sc;
	private BufferedReader br;
	private PrintWriter pw;
	private BufferedReader mes;
	private String name;

	public Sousin(BufferedReader b,PrintWriter p,BufferedReader m,Socket s,String n){
		sc = s;
		br = b;
		pw = p;
		mes = m;
		name = n;
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
