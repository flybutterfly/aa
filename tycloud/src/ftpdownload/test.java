package ftpdownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class test {
//	 * @param url FTP服务器hostname 
//	 * @param port FTP服务器端口 
//	 * @param username FTP登录账号 
//	 * @param password FTP登录密码 
//	 * @param remotePath FTP服务器上的相对路径 
//	 * @param localPath 下载后保存到本地的路径 
	public static boolean downFile(String url, int port,String username, String password, String remotePath,String localPath) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	    try {  
	        int reply;  
	        ftp.connect(url, port);
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
	        ftp.login(username, password);//登录   
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录   
	        FTPFile[] fs = ftp.listFiles();  
	        
	        Map<String,Boolean> map=new HashMap<String,Boolean>();
	        for(int i=0;i<fs.length;i++){
	        	map.put(fs[i].getName(),false);
	        }
	        
	        File file=new File(localPath);
	        for(int i=0;i<file.listFiles().length;i++){
	        	if(map.containsKey(file.listFiles()[i].getName())){
	        		map.put(file.listFiles()[i].getName(),true);
	        	}
	        }
	        
	        Iterator<Entry<String, Boolean>> it=map.entrySet().iterator();           
	        while(it.hasNext()){    
	                Map.Entry entry = (Map.Entry)it.next();           
	              if((Boolean)entry.getValue()==false){
	            	  File localFile = new File(localPath+"/"+entry.getKey().toString());  
		                OutputStream is = new FileOutputStream(localFile);   
		                ftp.retrieveFile(entry.getKey().toString(), is);  
		                is.close();  
	              }
	        }   

	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}

	public static void main(String args[]){
		downFile("192.168.42.1",21,"niuniu","niu1niu66","/","D:/test_download");
//		downFile("132.33.2.213",21,"cloud","Yun#1411","/","city.txt","D:/test_download");
	}
}

