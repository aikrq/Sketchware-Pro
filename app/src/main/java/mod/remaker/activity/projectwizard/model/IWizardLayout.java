package mod.remaker.activity.projectwizard.model;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

public interface IWizardLayout {
    int FORCE_NOT_ATTACH_VIEW = -2;

    boolean presentStep(WizardParams params);
    boolean addStep(WizardStep step, int position);
    void removeStep(WizardStep step, boolean immediate);
    boolean closeLastStep();
    void setSteps(List<WizardStep> steps);
    List<WizardStep> getSteps();
    boolean onBackPressed();

    static IWizardLayout newLayout(Context context) {
        return new WizardLayout(context);
    }

    default ViewGroup getView() {
        if (this instanceof ViewGroup) {
            return (ViewGroup) this;
        }
        throw new IllegalArgumentException("You should override `getView()` if you aren't inheriting from it.");
    }

    default Activity getParentActivity() {
        Context ctx = getView().getContext();
        if (ctx instanceof Activity) {
            return (Activity) ctx;
        }
        throw new IllegalArgumentException("IWizardLayout added in non-activity context!");
    }

    default boolean presentStep(WizardStep step) {
        return presentStep(new WizardParams(step));
    }

    default boolean addStep(WizardStep step) {
        return addStep(step, -1);
    }

    default void removeStep(int position) {
        if (position < 0 || position >= getSteps().size()) {
            return;
        }
        removeStep(getSteps().get(position));
    }

    default void removeStep(WizardStep step) {
        removeStep(step, false);
    }

    default void bringToFront(int i) {
        WizardStep step = getSteps().get(i);
        removeStep(step);
        addStep(step);
        // rebuildSteps(REBUILD_FLAG_REBUILD_ONLY_LAST);
    }

    class WizardParams {
        public WizardStep step;
        public boolean removeLast;

        public WizardParams(WizardStep step) {
            this.step = step;
        }

        public WizardParams setRemoveLast(boolean removeLast) {
            this.removeLast = removeLast;
            return this;
        }
    }
}
