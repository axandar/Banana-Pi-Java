package com.Axandar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class Sftp {
	
	private static ChannelSftp channelsftp;
	private static Session sessionsftp;
	
	public void connect(String ip, String username, String password, int port) throws JSchException{
		JSch jsch = new JSch();
		
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("compression.s2c", "zlib,none");
		config.put("compression.c2s", "zlib,none");
		
		sessionsftp = jsch.getSession(username, ip);
		sessionsftp.setConfig(config);
		sessionsftp.setPort(port);
		sessionsftp.setPassword(password);
		sessionsftp.connect();
		
		channelsftp = (ChannelSftp) sessionsftp.openChannel("sftp");
		channelsftp.connect();
	}
	
	public void channelsftp(String path_server) throws SftpException{
		String sftpWorkingDir = null;
		if(path_server == null){
			sftpWorkingDir = "axandar";
		}else{
			sftpWorkingDir = "axandar" + path_server;
		}
        channelsftp.cd(sftpWorkingDir);
	}
	
	public void send(String plik)throws Exception{
        /**@SuppressWarnings("unchecked")
		final Vector<LsEntry> files = channelsftp.ls(".");
        for (LsEntry obj : files) {
            System.out.println(obj.toString());
        }**/
        File f = new File(plik);
        channelsftp.put(new FileInputStream(f), f.getName());
	}
	
	public void nowy_katalog(String dir) throws SftpException{
		String currentDirectory=channelsftp.pwd();
		SftpATTRS attrs=null;
		try {
		    attrs = channelsftp.stat(currentDirectory+"/"+dir);
		} catch (Exception e) {
		    System.out.println(currentDirectory+"/"+dir+" not found");
		}

		if (attrs != null) {
		    System.out.println("Directory exists IsDir="+attrs.isDir());
		} else {
		    System.out.println("Creating dir "+dir);
		    channelsftp.mkdir(dir);
		}
	}

	public void download(String fileName, String filePath) throws SftpException, IOException{
		byte[] buffer = new byte[1024];
		BufferedInputStream bis = new BufferedInputStream(channelsftp.get(fileName));
		File newFile = new File(filePath);
		OutputStream os = new FileOutputStream(newFile);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		int readCount;
		while((readCount = bis.read(buffer)) > 0) {
			System.out.println("Writing: " );
			bos.write(buffer, 0, readCount);
		}
		bis.close();
		bos.close();
	}

	public void close_connection(){
		channelsftp.disconnect();
		sessionsftp.disconnect();
	}
}