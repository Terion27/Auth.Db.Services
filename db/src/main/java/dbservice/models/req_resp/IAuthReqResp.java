package dbservice.models.req_resp;

public interface IAuthReqResp<T> {
    String getMsg();

    T getData();
}
