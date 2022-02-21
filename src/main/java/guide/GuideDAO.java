package guide;

import java.util.List;

public interface GuideDAO {
    public void add(Guide guide);
    public List<Guide> getAll();
    public Guide getById(int id);
    public void updateById(int id, Guide update);
    public void deleteById(int id);
}
