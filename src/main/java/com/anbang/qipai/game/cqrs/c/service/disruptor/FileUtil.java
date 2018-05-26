package com.anbang.qipai.game.cqrs.c.service.disruptor;

import com.highto.framework.ddd.Command;
import com.highto.framework.nio.ByteBufferSerializer;
import com.highto.framework.util.RegexUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tan on 2016/8/30.
 */
public class FileUtil {
	public String getRecentFileName(String fileBasePath, String prefix) {
		File folder = new File(fileBasePath);
		// 获得folder文件夹下面所有文件
		String[] fileNames = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(prefix);
			}
		});
		String recentFileName = null;
		long recentCreateTime = 0;
		if (fileNames != null) {
			for (String fileName : fileNames) {
				long createTime = Long.parseLong(fileName.substring(prefix.length()));
				if (recentCreateTime < createTime) {
					recentFileName = fileName;
					recentCreateTime = createTime;
				}
			}
		}
		return recentFileName;
	}

	/**
	 * 注意 若cmd文件很大 。大小超过int最大值时 会有问题
	 *
	 * @param fileBasePath
	 * @param prefix
	 * @throws IOException
	 */
	public List<Command> read(String fileBasePath, String prefix) throws Throwable {
		String fileName = getRecentFileName(fileBasePath, prefix);
		List<Command> commands = new ArrayList<>();
		if (fileName != null) {
			RandomAccessFile file = new RandomAccessFile(fileBasePath + fileName, "r");
			FileChannel channel = file.getChannel();
			long size = channel.size();
			ByteBuffer buffer = ByteBuffer.allocate((int) size);
			channel.read(buffer);

			buffer.flip();
			while (buffer.hasRemaining()) {
				Command command = ByteBufferSerializer.byteBufferToObj(buffer);
				commands.add(command);
			}
		}
		return commands;
	}

	public static String uploadImg(HttpServletRequest request, String saveDirUrl) throws Exception {
		// 获取服务器路径
		String realpathStr = request.getServletContext().getRealPath("/");
		String saveDir = realpathStr + saveDirUrl;

		List<MultipartFile> list = multipart(request);
		if (list.size() > 0) {
			MultipartFile file = list.get(0);
			String originalFileName = file.getOriginalFilename();
			// 文件后缀
			String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
			String regex = "([p,P][s,S][d,D])|([j,J][p,P][g,G])|([j,J][p,P][e,E][g,G])|([g,G][i,I][f,F])|([p,P][n,N][g,G])|([b,B][m,M][p,P])";
			if (RegexUtil.isMatch(fileType.substring(fileType.lastIndexOf(".") + 1), regex)) {

				// 重命名上传后的文件名
				String saveName = (int) (Math.random() * 100) + "" + System.currentTimeMillis() + fileType;
				File file2 = new File(saveDir);
				if (!file2.exists()) {
					file2.mkdirs();
				}
				// 上传
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(saveDir, saveName));
					return saveName;
				} catch (IOException e) {
					throw new Exception("uploadfail");
				}
			} else {
				throw new Exception("filetypewrong");
			}
		} else {
			throw new Exception("nofile");
		}
	}

	private static List<MultipartFile> multipart(HttpServletRequest request) {
		ArrayList<MultipartFile> list = new ArrayList<MultipartFile>();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				list.add(file);
			}
		}
		return list;
	}
}
