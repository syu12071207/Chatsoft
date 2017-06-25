
import java.io.*;
import java.net.*;

public class ChatS {
	//ポート番号
	public static int PORT;

	public static void main(String[] args) {
		//ポート番号の指定
		try{
			BufferedReader key = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("ポート番号を入力してください");
			PORT=Integer.parseInt(key.readLine());

			System.out.println("相手に表示する名前を入力してください");
			String name = key.readLine();

			ChatS cs = new ChatS();
			//接続
			ServerSocket ss = new ServerSocket(PORT);

			System.out.println("待機します");

			while(true){
				try{
					Socket sc = ss.accept();

					System.out.println("接続しました");
					//スレッド起動
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
//相手との処理
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
		//送受信準備
		try{
			//受信
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			//送信
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));
			//入力
			mes = new BufferedReader(new InputStreamReader(System.in));

		}catch(Exception e){
			e.printStackTrace();
		}

		//送受信
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

					System.out.println("切断されました");

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

					System.out.println("切断されました");

					System.exit(0);

				}catch (Exception ex) {
					e.printStackTrace();
				}
			}
		}
	}
}
