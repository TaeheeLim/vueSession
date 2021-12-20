package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        return o1.getIssueIdx() < o2.getIssueIdx() ? 1 : -1;
    }

    private static int compare(Calendar o1, Calendar o2) {
        return o1.getCalIdx() < o2.getCalIdx() ? 1 : -1;
    }

    public ProjectDTO entityToDto() {
        List<IssueDTO> issue = new ArrayList<>();
        if(!issueList.isEmpty()) {
            issueList.sort(Project::compare);
            for(int i = 0; i < 2; i++) {
                issue.add(issueList.get(i).entityToDto());
            }
        }

        List<CalendarDTO> calendar = new ArrayList<>();
        if(!calendarList.isEmpty()) {
            calendarList.sort(Project::compare);
            for(int i = 0; i < 3; i++) {
                calendar.add(calendarList.get(i).entityToDto());
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
