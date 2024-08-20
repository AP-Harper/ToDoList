package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepo memberRepo;
    @InjectMocks
    private MemberServiceImpl memberService;

    Member member1;
    Member member2;
    ToDo toDo;
    ToDo toDo2;



    @BeforeEach
    void setUp() {
        member1 = new Member("Member 1");
        member1.setId(1L);
        member1.setName("Andrew");
        member2 = new Member("Member 2");
        toDo = new ToDo("Wash", member1);
        toDo2 = new ToDo("Clean", member1);
    }

    @Test
    void findById() {
        Long memberId = 1L;
        member1.setId(memberId);
        member1.setName("Test");
        when(memberRepo.findById(1L)).thenReturn(Optional.of(member1));
        Member result = memberService.getMemberById(memberId);
        assertNotNull(result);
        assertEquals("Test", result.getName());
        verify(memberRepo, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void findAll() {
        List<Member> memberList = Arrays.asList(member1, member2);
        when(memberService.findAll()).thenReturn(memberList);
        List<Member> result = memberService.findAll();
        assertEquals(memberList.size(), result.size());
        verify(memberRepo, times(1)).findAll();
    }


    //Not working
    @Test
    void findUserTasks() {
        Long memberId = 1L;

        when(memberRepo.findById(1L)).thenReturn(Optional.of(member1));
        Member result = memberService.getMemberById(memberId);

        result.setId(1L);
        member1.setId(1L);
        List<ToDo> memberTasks = Arrays.asList(toDo, toDo2);

        List<ToDo> result2 = memberService.findUserTasks(result.getId());
        assertEquals(memberTasks.size(), result2.size());
        verify(memberService, times(1)).findUserTasks(Mockito.anyLong());
    }

    // Not working
    @Test
    void findUserCompleted() {
        Member member3 = new Member("Test");
        member3.setId(3L);
        member1.setId(1L);
        toDo.setId(1L);
        toDo2.setId(2L);
        toDo.setCompleted(true);
        toDo2.setCompleted(true);
        List<ToDo> completedTasks = Arrays.asList(toDo, toDo2);
        when(memberRepo.findById(1L)).thenReturn(Optional.of(member1));
        when(memberService.getMemberById(Mockito.anyLong())).thenReturn(member1);

        when(memberService.findUserCompleted(1L)).thenReturn(completedTasks);
        List<ToDo> result = memberService.findUserCompleted(Mockito.anyLong());
        assertNotNull(result);
        assertEquals(completedTasks.size(), result.size());
    }

    @Test
    void findUserIncomplete() {
    }

    @Test
    void addMember() {
        Member memberToDo = new Member("Test Add");
        memberToDo.setId(1L);
        when(memberService.addMember(Mockito.any(Member.class))).thenReturn(memberToDo);

        Member result = memberService.addMember(memberToDo);

        assertNotNull(result);
        assertEquals("Test Add", result.getName());
        assertEquals(1L, memberToDo.getId());
        verify(memberRepo, times(1)).save(Mockito.any(Member.class));
    }
}