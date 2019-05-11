package hse.se.aaizmaylov.petrinetscalculationserver.repository;

import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestInfo;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestInfoRepositoryTest {

    @Autowired RequestInfoRepository requestInfoRepository;

    @After
    public void tearDown() {
        requestInfoRepository.deleteAll();
    }

    @Test
    public void check() {
        requestInfoRepository.save(new RequestInfo("lol", RequestType.REDUCTION));

        List<RequestInfo> requestInfos = (List<RequestInfo>) requestInfoRepository.findAll();

        assertEquals(1, requestInfos.size());
    }

    @Test
    public void checkUpdating() {
        RequestInfo info = requestInfoRepository.save(new RequestInfo("file", RequestType.ANALYSIS));

        info.setOriginalFileName("lol_file");
        info.getReductions().add("A");
        info.getReductions().add("B");
        info.setRequestType(RequestType.REDUCTION);

        requestInfoRepository.save(info);

        RequestInfo otherInfo = requestInfoRepository.findById(info.getId()).orElseThrow(AssertionError::new);

        assertEquals("lol_file", otherInfo.getOriginalFileName());
        assertEquals(2, otherInfo.getReductions().size());
        assertTrue(otherInfo.getReductions().containsAll(Arrays.asList("A", "B")));
        assertEquals(RequestType.REDUCTION, info.getRequestType());
    }
}