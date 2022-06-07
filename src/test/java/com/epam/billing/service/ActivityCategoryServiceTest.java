package com.epam.billing.service;

import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.repository.ActivityCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ActivityCategoryServiceTest {

    private ActivityCategoryService activityCategoryService;
    
    @Mock
    ActivityCategoryRepository activityCategoryRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnById() {
        ActivityCategory expected = new ActivityCategory().setCategoryId(1);
        
        when(activityCategoryRepository.getById(1)).thenReturn(expected);
        activityCategoryService = new ActivityCategoryService(activityCategoryRepository);
        
        ActivityCategory actual = activityCategoryService.getById(1);
        
        assertEquals(expected, actual);
    }
}