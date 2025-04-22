package no.ntnu.gr10.bachelor_rest_api.fisheryActivity;

import no.ntnu.gr10.bachelor_rest_api.company.Company;
import no.ntnu.gr10.bachelor_rest_api.company.CompanyService;
import no.ntnu.gr10.bachelor_rest_api.excption.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.dto.CreateFisheryActivity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;
  final CompanyService companyService;

  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository, CompanyService companyService){
    this.fisheryActivityRepository = fisheryActivityRepository;
    this.companyService = companyService;
  }

  public List<FisheryActivity> getAllFisheryActivitiesWithCompanyId(Integer companyId){
    return fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());
  }

  public FisheryActivity getByIdAndCompanyId(Long id, Integer companyId){
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue());
  }

  public FisheryActivity createFisheryActivity(CreateFisheryActivity cmd, Integer companyId) throws CompanyNotFoundException {
    Company company = companyService.getCompanyById(companyId.longValue());
    FisheryActivity fa = new FisheryActivity();
    fa.setCompany(company);
    fa.setSetupDateTime(cmd.setupDateTime());
    fa.setToolTypeCode(cmd.toolTypeCode());
    fa.setToolTypeName(cmd.toolTypeName());
    fa.setToolId(cmd.toolId());
    fa.setLastChangedDateTime(cmd.lastChangedDateTime());
    fa.setStartingPointLat(cmd.startingPointLat());
    fa.setStartingPointLon(cmd.startingPointLon());
    fa.setLength(cmd.length());
    fa.setGeometry(cmd.geometry());
    return fisheryActivityRepository.save(fa);
  }

  public FisheryActivity updateForCompany(Long id,
                                          CreateFisheryActivity cmd,
                                          Integer companyId) {
    FisheryActivity existing = getByIdAndCompanyId(id, companyId);
    existing.setSetupDateTime(cmd.setupDateTime());
    existing.setToolTypeCode(cmd.toolTypeCode());
    existing.setToolTypeName(cmd.toolTypeName());
    existing.setToolId(cmd.toolId());
    existing.setRemovedDateTime(cmd.removedDateTime());
    existing.setLastChangedDateTime(cmd.lastChangedDateTime());
    existing.setStartingPointLat(cmd.startingPointLat());
    existing.setStartingPointLon(cmd.startingPointLon());
    existing.setLength(cmd.length());
    existing.setGeometry(cmd.geometry());
    return fisheryActivityRepository.save(existing);
  }

  public void deleteByIdAndCompanyId(Long id, Integer companyId) {
    FisheryActivity fa = getByIdAndCompanyId(id, companyId);
    fisheryActivityRepository.delete(fa);
  }

}
