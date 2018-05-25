package org.firespeed.phpconfukuoka18.adapter

import org.firespeed.phpconfukuoka18.model.Session
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class TimeTableAdapterTest {
    @Test
    fun setItemTest() {
        val adapter = TimeTableAdapter()
        val list =ArrayList<Session>()
        list.add(Session(1L,"location","11:11",1,"title",null,null,null,null,false))
        list.add(Session(2L,"location","11:11",1,"title",null,null,null,null,false))
        adapter.setItem(list)
        Assert.assertEquals(adapter.itemCount,3)
        Assert.assertEquals(adapter.getItemViewType(0),TimeTableAdapter.VIEW_TYPE_TIME)
        Assert.assertEquals(adapter.getItemViewType(1),TimeTableAdapter.VIEW_TYPE_SESSION)
        Assert.assertEquals(adapter.getItemViewType(2),TimeTableAdapter.VIEW_TYPE_SESSION)
        adapter.setItem(list)
        Assert.assertEquals(adapter.itemCount,3)

        list.add(Session(3L,"location3","12:12",2,"title",null,null,null,null,false))
        list.add(Session(4L,"location4","12:12",2,"title",null,null,null,null,false))

        adapter.setItem(list)
        Assert.assertEquals(adapter.itemCount,6)

        Assert.assertEquals(adapter.getItemViewType(0),TimeTableAdapter.VIEW_TYPE_TIME)
        Assert.assertEquals(adapter.getItemViewType(1),TimeTableAdapter.VIEW_TYPE_SESSION)
        Assert.assertEquals(adapter.getItemViewType(2),TimeTableAdapter.VIEW_TYPE_SESSION)

        Assert.assertEquals(adapter.getItemViewType(3),TimeTableAdapter.VIEW_TYPE_TIME)
        Assert.assertEquals(adapter.getItemViewType(4),TimeTableAdapter.VIEW_TYPE_SESSION)
        Assert.assertEquals(adapter.getItemViewType(5),TimeTableAdapter.VIEW_TYPE_SESSION)

    }

}