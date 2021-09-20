package com.sendshare.movecopydata.wififiletransfer.interfaces;

public interface VisualizeDataTransfer {
     void visualize(double partByte, double allByte);

     void showFileName(String fileName);

     void handleConnectionError(String text);
}
