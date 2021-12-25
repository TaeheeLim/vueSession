package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.KanbanItemDTO;
import com.kanboo.www.service.inter.project.KanbanItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban")
public class KanbanController {

    private final KanbanItemService kanbanItemService;

    @PostMapping("/insert")
    public KanbanItemDTO insertKanbanItem(KanbanItemDTO kanbanItemDTO){
        return kanbanItemService.insertKanbanItem(kanbanItemDTO);
    }

    @GetMapping("/getAll")
    public List<KanbanItemDTO> getAllKanbanItemsByMemIdxAndPrjctIdx(KanbanItemDTO kanbanItemDTO){
//		System.out.println(kanbanItemDTO);
//		return null;
        return kanbanItemService.getAllKanbanItemsByMemIdxAndPrjctIdx(kanbanItemDTO);
    }

    @PostMapping("/update")
    public void updateKanbanItem(KanbanItemDTO kanbanItemDTO){
        System.out.println(kanbanItemDTO);
        kanbanItemService.updateKanbanItem(kanbanItemDTO);
    }

    @PostMapping("/delete")
    public void deleteKanbanItem(Long kbItmIdx){
        kanbanItemService.deleteKanbanItem(kbItmIdx);
    }

}
