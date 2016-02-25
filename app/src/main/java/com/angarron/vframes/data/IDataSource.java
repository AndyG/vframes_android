package com.angarron.vframes.data;

import data.model.IDataModel;

public interface IDataSource {

    enum FetchFailureReason {
        READ_FROM_FILE_FAILED,
        UNSUPPORTED_CLIENT_VERSION,
        NETWORK_ERROR,
        UNKNOWN_ERROR
    }

    void fetchData(Listener listener);

    interface Listener {
        void onDataReceived(IDataModel data, boolean wasUpdated);
        void onDataFetchFailed(FetchFailureReason failureReason);
    }
}
