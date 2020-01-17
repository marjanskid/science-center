package root.sciencecenter.services;

import root.sciencecenter.dtos.FormSubmissionDto;

import java.util.List;

public interface MagazineService {

    boolean checkMagazineExists(List<FormSubmissionDto> dto);
}
