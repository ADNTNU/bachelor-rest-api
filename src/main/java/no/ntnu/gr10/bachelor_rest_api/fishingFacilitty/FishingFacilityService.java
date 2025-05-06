package no.ntnu.gr10.bachelor_rest_api.fishingFacilitty;

import no.ntnu.gr10.bachelor_rest_api.excption.FishingFacilityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingFacilityService {

  final FishingFacilityRepository fishingFacilityRepository;

  public FishingFacilityService(FishingFacilityRepository fishingFacilityRepository){
    this.fishingFacilityRepository = fishingFacilityRepository;
  }

  public List<FishingFacility> getAllFishingFacilitiesWithCompanyId(Integer companyId){
    return fishingFacilityRepository.findAllByCompany_Id(companyId.longValue());
  }

  public FishingFacility getByIdAndCompanyId(Long id, Integer companyId){
    return fishingFacilityRepository.findFishingFacilitiesByIdAndCompany_Id(id, companyId.longValue())
            .orElseThrow(() -> new FishingFacilityNotFoundException("Could not find Fishing Facility with that ID!"));
  }


}
