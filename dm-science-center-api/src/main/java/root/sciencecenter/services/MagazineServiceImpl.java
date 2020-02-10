package root.sciencecenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.repositories.MagazineRepository;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public boolean checkMagazineExists(List<FormSubmissionDto> dto) {
        System.out.println("checkMagazineExists");
        boolean retValue = false;
        System.out.println("issnNumber: " + dto.get(1).getFieldValue());

        final Long issnNumber = getLongValue(dto.get(1).getFieldValue());
        System.out.println("issnNumber: " + issnNumber);

        if (issnNumber != null) {
            System.out.println("issn nije null");
            Magazine magazine = magazineRepository.findByIssnNumber(issnNumber);
            if (magazine != null) {
                System.out.println("postoji magazin s datim issn brojem");
                retValue = true;
            }
        } else {
            System.out.println("issn je null");
            retValue = true;
        }

        return retValue;
    }

    @Override
    public Magazine getMagazineByName(FormSubmissionDto dto) {
        String magazineName = dto.getFieldValue();
        System.out.println("magazineName: " + magazineName);
        return magazineRepository.findByName(magazineName);
    }

    @Override
    public List<Magazine> getAll() {
        return magazineRepository.findAll();
    }

    private Long getLongValue(String number) {
        try {
            return Long.parseLong(number);
        }
        catch(NumberFormatException e) {
            System.out.println("nfe exception");
            return null;
        }
    }
}
