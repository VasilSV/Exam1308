package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportBookDTO;
import softuni.exam.models.dto.ImportLibraryMemberDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private static final String LIBRARY_FILE_PATH="src/main/resources/files/json/library-members.json";


    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository,
                                    ModelMapper modelMapper, ValidationUtil validationUtil,
                                    Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return libraryMemberRepository.count()>0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(LIBRARY_FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readLibraryMembersFileContent(), ImportLibraryMemberDTO[].class))
                .filter(importLibraryMemberDTO -> {
                    boolean isValid = validationUtil.isValid(importLibraryMemberDTO);

                    boolean doesntExist = libraryMemberRepository
                            .findLibraryMemberByPhoneNumber(importLibraryMemberDTO.getPhoneNumber()).isEmpty();
                    if (!doesntExist) {
                        isValid = false;
                    }


                    sb.append(isValid ?
                                    String.format("Successfully imported library member %s - %s"
                                            , importLibraryMemberDTO.getFirstName()
                                            , importLibraryMemberDTO.getLastName())
                                    : "Invalid library member")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(importLibraryMemberDTO -> {
                    LibraryMember libraryMember
                            = modelMapper.map(importLibraryMemberDTO, LibraryMember.class);

                    return libraryMember;
                })
                .forEach(libraryMemberRepository::save);

        return sb.toString();

    }
}
