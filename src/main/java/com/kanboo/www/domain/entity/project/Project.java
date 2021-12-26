package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
@Builder
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prjctIdx;

    private String prjctNm;
    private LocalDate prjctStartDate;
    private LocalDate prjctEndDate;
    private int prjctProgress;
    private String prjctDelAt;
    private String prjctComplAt;
    private String prjctReadMe;

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Issue> issueList = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Calendar> calendarList = new ArrayList<>();

    private static int compare(Issue o1, Issue o2) {
        return o1.getIssueIdx() <= o2.getIssueIdx() ? 1 : -1;
    }

    private static int compare(Calendar o1, Calendar o2) {
        return o1.getCalIdx() <= o2.getCalIdx() ? 1 : -1;
    }

    public void changePrjctDelAt(String prjctDelAt){
        this.prjctDelAt = prjctDelAt;
    }

    public void changePrjctComplAt(String prjctComplAt){
        this.prjctComplAt = prjctComplAt;
    }

    public ProjectDTO entityToDto() {
        List<IssueDTO> issue = new ArrayList<>();
        if(issueList != null && !issueList.isEmpty()) {
            issueList.sort(Project::compare);
            int length = issueList.size();
            if(issueList.size() >= 2) {
                length = 2;
            }
            for(int i = 0; i < length; i++) {
                IssueDTO issueDTO = issueList.get(i).entityToDto();
                issueDTO.setProject(ProjectDTO.builder().prjctIdx(prjctIdx).build());
                issue.add(issueDTO);
            }
        }

        List<CalendarDTO> calendar = new ArrayList<>();
        if(calendarList != null && !calendarList.isEmpty()) {
            calendarList.sort(Project::compare);
            int length = calendarList.size();
            if(calendarList.size() >= 3) {
                length = 3;
            }
            for(int i = 0; i < length; i++) {
                CalendarDTO calendarDTO = calendarList.get(i).entityToDto();
                calendarDTO.setProject(ProjectDTO.builder().prjctIdx(prjctIdx).build());
                calendar.add(calendarDTO);
            }
        }


        return ProjectDTO.builder()
                .prjctIdx(prjctIdx)
                .prjctNm(prjctNm)
                .prjctStartDate(prjctStartDate)
                .prjctEndDate(prjctEndDate)
                .prjctProgress(prjctProgress)
                .prjctDelAt(prjctDelAt)
                .prjctComplAt(prjctComplAt)
                .prjctReadMe(prjctReadMe)
                .issueList(issue)
                .calendarList(calendar)
                .build();
    }

    public void updateReadMe(String prjctReadMe) {
        this.prjctReadMe = prjctReadMe;
    }

    public void changeIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public void changeCalendarList(List<Calendar> calendarList) {
        this.calendarList = calendarList;
    }
}