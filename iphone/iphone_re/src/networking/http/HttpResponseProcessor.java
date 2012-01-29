package networking.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public abstract interface HttpResponseProcessor
{
  public abstract void processRespose(Object paramObject);

  public abstract void processFileNotFoundException(FileNotFoundException paramFileNotFoundException);

  public abstract void processUnknownHostException(UnknownHostException paramUnknownHostException);

  public abstract void processSocketTimeoutException(SocketTimeoutException paramSocketTimeoutException);

  public abstract void processIOException(IOException paramIOException);

  public abstract void processException(Exception paramException);

  public abstract void processConnectionClosed();
}

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.HttpResponseProcessor
 * JD-Core Version:    0.6.0
 */