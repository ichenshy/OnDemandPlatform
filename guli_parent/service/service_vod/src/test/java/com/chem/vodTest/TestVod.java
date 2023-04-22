package com.chem.vodTest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/813:40
 */
public class TestVod {

    @Test
    public void Test() throws ClientException {
        //视频播放地址
        //创建对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6LEVfpiTAfrqvJhP81", "VpDReQlppp50tlE8kN0vO7qjZkGUcN");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象设置视频id
        request.setVideoId("6d041c26b1aa4d1981a4f6729c74e062");
        //调用初始化对象里面的方法，传递request,获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("playInfo.playURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    //视频凭证
    @Test
    public void Test1() throws ClientException {
        //创建对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6LEVfpiTAfrqvJhP81", "VpDReQlppp50tlE8kN0vO7qjZkGUcN");
        //创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request对象设置视频id
        request.setVideoId("6d041c26b1aa4d1981a4f6729c74e062");
        //调用初始化对象里面的方法，传递request,获取数据
        response = client.getAcsResponse(request);
        System.out.println("凭证:"+response.getPlayAuth());
}
//上传视频
    @Test
    public void test2(){
        String accessKeyId="LTAI5t6LEVfpiTAfrqvJhP81";
        String accessKeySecret="VpDReQlppp50tlE8kN0vO7qjZkGUcN";
        String title="6 - What If I Want to Move Faster";//上传后文件的名称
        String fileName="D:/Data/java/谷粒学苑/资料笔记课件源码/项目资料/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";//本地文件的路径和名称
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


}
