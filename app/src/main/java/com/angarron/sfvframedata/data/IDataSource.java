package com.angarron.sfvframedata.data;

public interface IDataSource {

    enum FetchFailureReason {
        READ_FROM_FILE_FAILED,
        UNKNOWN_ERROR
    }

    void fetchData(Listener listener);

    interface Listener {
        void onDataReceived(IDataModel data);
        void onDataFetchFailed(FetchFailureReason failureReason);
    }
}
