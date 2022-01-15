package com.hutech.lib.ResultModel;

public class OrderResultModel {
    private Data data;

    private String message;

    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[status = " + status + ", data = " + data + ", message = " + message + "]";
    }

    public class Data {
        private int kot_Id;

        private int kot_StatusID;

        public int getKOT_Id() {
            return kot_Id;
        }

        public void setKoT_Id(int kot_Id) {
            this.kot_Id = kot_Id;
        }

        public int getKoT_StatusID() {
            return kot_StatusID;
        }

        public void setKoT_StatusID(int kot_StatusID) {
            this.kot_StatusID = kot_StatusID;
        }

        @Override
        public String toString() {
            return "[kot_Id = " + kot_Id + ", kot_StatusID = " + kot_StatusID + "]";
        }
    }

}
