package cn.jzvd.demo;

/**
 * Created by admin on 11/25/2017.
 */

public class VideoDetails
{
    String _id,name,size,data;

    public VideoDetails(String _id, String data, String name,String size)
    {
        this._id=_id;
        this.data=data;
        this.name=name;
        this.size=size;
    }

    public String get_id() {
        return _id;
    }

    public String getdata() {
        return data;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}

