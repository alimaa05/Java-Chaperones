package guide;

import java.util.List;

public interface GuideDAO {
    public int add(Guide guide);
    public List<Guide> getAll();
    public Guide getById(int id);
    public int updateById(int id, Guide update);
    public int deleteById(int id);
}
