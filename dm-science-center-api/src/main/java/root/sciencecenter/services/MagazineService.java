package root.sciencecenter.services;

import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;

import java.util.List;

public interface MagazineService {

    boolean checkMagazineExists(List<FormSubmissionDto> dto);
    Magazine getMagazineByName(FormSubmissionDto dto);

    List<Magazine> getAll();
}
