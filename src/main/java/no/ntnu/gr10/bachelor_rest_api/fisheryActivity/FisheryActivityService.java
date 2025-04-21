package no.ntnu.gr10.bachelor_rest_api.fisheryActivity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;

  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository){
    this.fisheryActivityRepository = fisheryActivityRepository;
  }

  public List<FisheryActivity> getAllFisheryActivitiesWithCompanyId(Integer companyId){
    return fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());
  }

  public FisheryActivity createFisheryActivity(FisheryActivity fisheryActivity){
    return fisheryActivityRepository.save(fisheryActivity);
  }

}
