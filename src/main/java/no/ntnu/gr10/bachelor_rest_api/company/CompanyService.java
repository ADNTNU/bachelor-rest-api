package no.ntnu.gr10.bachelor_rest_api.company;

import no.ntnu.gr10.bachelor_rest_api.excption.CompanyNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
  final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository){
    this.companyRepository = companyRepository;
  }

  public Company getCompanyById(Long companyId) throws CompanyNotFoundException{
    return companyRepository.findById(companyId)
            .orElseThrow(() -> new CompanyNotFoundException("Company not found"));
  }

}
