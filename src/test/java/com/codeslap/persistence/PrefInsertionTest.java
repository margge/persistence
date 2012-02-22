package com.codeslap.persistence;

import android.app.Activity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author cristian
 */
public class PrefInsertionTest extends PrefTest{
    @Test
    public void shouldInsertAndRetrieveTheSameBean() {
        PreferencesAdapter adapter = Persistence.getPreferenceAdapter(new Activity());

        // should retrieve an empty object when it is possible to create one
        assertNotNull(adapter.retrieve(PreferenceBean.class));

        PreferenceBean bean = new PreferenceBean();
        bean.veryLong = Long.MAX_VALUE;
        bean.name = "Foo Bar Baz";
        bean.bool = true;
        bean.decimal = Float.MAX_VALUE;
        bean.number = Integer.MAX_VALUE;
        
        adapter.store(bean);

        PreferenceBean found = adapter.retrieve(PreferenceBean.class);
        assertEquals(bean, found);
        
        // now let's delete everything
        assertTrue(adapter.delete(PreferenceBean.class));
        // it should now have default values
        PreferenceBean retrieve = adapter.retrieve(PreferenceBean.class);
        assertEquals(0, retrieve.number);
        assertEquals(null, retrieve.name);
        assertEquals(0f, retrieve.decimal, 0);
        assertEquals(false, retrieve.bool);
        assertEquals(0L, retrieve.veryLong);
    }
    @Test
    public void shouldInsertAndRetrieveTheSameBeanWithAnnotations() {
        PreferencesAdapter adapter = Persistence.getPreferenceAdapter(new Activity());

        // should retrieve an empty object when it is possible to create one
        assertNotNull(adapter.retrieve(PrefWithAnnotations.class));

        PrefWithAnnotations bean = new PrefWithAnnotations();
        bean.veryLong = Long.MAX_VALUE;
        bean.name = "Foo Bar Baz";
        bean.bool = true;
        bean.decimal = Float.MAX_VALUE;
        bean.number = Integer.MAX_VALUE;

        adapter.store(bean);

        PrefWithAnnotations found = adapter.retrieve(PrefWithAnnotations.class);
        assertEquals(bean, found);

        // now let's the delete the bean and when retrieving it again, it should have fell to default values
        assertTrue(adapter.delete(PrefWithAnnotations.class));

        PrefWithAnnotations retrieve = adapter.retrieve(PrefWithAnnotations.class);
        assertEquals(Integer.MIN_VALUE, retrieve.number);
        assertEquals(Float.MIN_VALUE, retrieve.decimal, 0);
        assertEquals(Long.MIN_VALUE, retrieve.veryLong, 0);
        assertEquals("foo bar", retrieve.name);
        assertTrue(retrieve.bool);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenBeanIsNotAssociated() {
        PreferencesAdapter adapter = Persistence.getPreferenceAdapter(new Activity());
        adapter.store(String.class);
    }
}
