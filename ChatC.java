
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
			System.out.println("ホスト名を入力してください");
			HOST = mes.readLine();

			System.out.println("ポート番号を入力してください");
			PORT = Integer.parseInt(mes.readLine());

			System.out.println("相手に表示する名前を入力してください");
			name = mes.readLine();

			//接続
			sc = new Socket(HOST,PORT);
			System.out.println("接続しました");
			//送受信準備
			//受信
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			//送信
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
			//送受信
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

					System.out.println("切断されました");

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

					System.out.println("切断されました");

					System.exit(0);

				}catch (Exception ex) {
					e.printStackTrace();
				}
			}
		}
	}
}