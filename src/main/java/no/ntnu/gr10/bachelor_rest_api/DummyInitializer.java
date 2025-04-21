package no.ntnu.gr10.bachelor_rest_api;

import no.ntnu.gr10.bachelor_rest_api.company.Company;
import no.ntnu.gr10.bachelor_rest_api.company.CompanyRepository;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.FisheryActivity;
import no.ntnu.gr10.bachelor_rest_api.fisheryActivity.FisheryActivityRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DummyInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private final CompanyRepository companyRepository;
  private final FisheryActivityRepository fisheryActivityRepository;

  public DummyInitializer(
          CompanyRepository companyRepository,
          FisheryActivityRepository fisheryActivityRepository
  ){
    this.companyRepository = companyRepository;
    this.fisheryActivityRepository = fisheryActivityRepository;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    if (fisheryActivityRepository.count() == 0) {
      Company company = new Company("BigBoyClub2");
      companyRepository.save(company);
      FisheryActivity fa = new FisheryActivity();
      fa.setCompany(company);
      fa.setSetupDateTime(LocalDateTime.parse("2025-04-20T01:47:10"));
      fa.setToolTypeCode("NETS");
      fa.setToolTypeName("Nets");
      fa.setToolId("31e47253-6d8a-4060-942b-aec5ad0d4d12");
      // no removed date in example → leave null
      fa.setLastChangedDateTime(LocalDateTime.parse("2025-04-20T02:14:26"));
      fa.setStartingPointLat(58.07933);
      fa.setStartingPointLon(3.60231);
      fa.setLength(5321.061133053197);
      fa.setGeometry(
              "LINESTRING(" +
                      "3.60231 58.07933," +
                      "3.61934 58.07934," +
                      "3.61947 58.07687," +
                      "3.60400 58.07596," +
                      "3.60497 58.07362," +
                      "3.61921 58.07355," +
                      "3.61915 58.07121," +
                      "3.60383 58.07022," +
                      "3.60480 58.06797," +
                      "3.61095 58.06787," +
                      "3.61130 58.06587" +
                      ")"
      );

      // 3) persist
      fisheryActivityRepository.save(fa);
    }
  }

}
