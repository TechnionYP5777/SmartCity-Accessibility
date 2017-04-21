package smartcity.accessibility.services;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewService {
	@RequestMapping("/reviews")
    public String test() {
    	return "Review added successfuly";
    }
}