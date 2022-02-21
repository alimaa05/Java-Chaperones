package com.chaperones.guide;

import java.util.List;

public interface GuideDAO {
    public int add(Guide guide);
    public List<Guide> getAll();
    public Guide getById(Integer id);
    public int updateById(Integer id, Guide update);
    public int deleteById(Integer id);
}
