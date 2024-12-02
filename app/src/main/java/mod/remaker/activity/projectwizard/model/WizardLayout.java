package mod.remaker.activity.projectwizard.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import mod.remaker.activity.projectwizard.ProjectWizardActivity;

import pro.sketchware.R;
import pro.sketchware.utility.ThemeUtils;

import java.util.List;

public class WizardLayout extends FrameLayout implements IWizardLayout {
    public FrameLayout containerView;
    public FrameLayout containerViewBack;

    private WizardStep newStep;
    private WizardStep oldStep;

    private ProjectWizardActivity activity;
    private List<WizardStep> steps;

    public WizardLayout(Context context) {
        super(context);
        activity = (ProjectWizardActivity) context;
    }

    @Override
    public boolean presentStep(IWizardLayout.WizardParams params) {
        WizardStep step = params.step;
        boolean removeLast = params.removeLast;

        if (step == null) {
            return false;
        }

        WizardStep currentStep = getLastStep();
        step.setParentLayout(this);

        View stepView = step.getStepView();
        if (stepView == null) {
            stepView = step.onCreateView(activity);
        }

        removeViewFromParent(stepView);
        View wrappedView = stepView;
        containerViewBack.addView(wrappedView);
        wrappedView.setLayoutParams(new LayoutParams(-1, -1));
        steps.add(step);

        FrameLayout temp = containerView;
        containerView = containerViewBack;
        containerViewBack = temp;
        containerView.setVisibility(View.VISIBLE);

        bringChildToFront(containerView);
        presentStepInternalRemoveOld(removeLast, currentStep);

        return true;
    }

    @Override
    public boolean addStep(WizardStep step, int position) {
        if (steps.contains(step)) return false;
        step.setParentLayout(this);

        if (position == -1 || position == IWizardLayout.FORCE_NOT_ATTACH_VIEW) {
            if (!steps.isEmpty()) {
                WizardStep previousStep = getLastStep();
                if (previousStep.getStepView() != null) {
                    removeViewFromParent(previousStep.getStepView());
                }
            }
            steps.add(step);
            if (position != IWizardLayout.FORCE_NOT_ATTACH_VIEW) {
                attachView(step);
            }
        } else {
            if (position != IWizardLayout.FORCE_NOT_ATTACH_VIEW) {
                position = 0;
                attachView(step, position);
            }
            steps.add(position, step);
        }
        return true;
    }

    @Override
    public void removeStep(WizardStep step, boolean immediate) {
        removeStepInternal(step, !immediate);
    }

    @Override
    public boolean closeLastStep() {
        WizardStep step = getLastStep();
        if (step != null && !step.allowCloseStep()) {
            return false;
        }

        WizardStep currentStep = getLastStep();
        WizardStep previousStep = null;
        if (steps.size() > 1) {
            previousStep = steps.get(steps.size() - 2);
        }

        if (previousStep != null) {
            FrameLayout temp = containerView;
            containerView = containerViewBack;
            containerViewBack = temp;

            previousStep.setParentLayout(this);
            View stepView = previousStep.getStepView();
            if (stepView == null) {
                stepView = previousStep.onCreateView(activity);
            }

            containerView.setVisibility(View.VISIBLE);
            removeViewFromParent(stepView);
            containerView.addView(stepView);
            stepView.setLayoutParams(new LayoutParams(-1, -1));

            newStep = previousStep;
            oldStep = currentStep;

            checkStepViewBackground(stepView);
            closeLastStepInternalRemoveOld(currentStep);
        } else {
            removeStepInternal(currentStep, false);
            return false;
        }

        return true;
    }

    @Override
    public void setSteps(List<WizardStep> steps) {
        this.steps = steps;

        if (containerViewBack != null) {
            removeViewFromParent(containerViewBack);
        }

        containerViewBack = new FrameLayout(activity);
        containerViewBack.setLayoutParams(new LayoutParams(-1, -1));
        addView(containerViewBack);

        containerView = new FrameLayout(activity);
        containerView.setLayoutParams(new LayoutParams(-1, -1));
        addView(containerView);

        for (WizardStep step : steps) {
            step.setParentLayout(this);
        }
    }

    @Override
    public List<WizardStep> getSteps() {
        return steps;
    }

    @Override
    public boolean onBackPressed() {
        WizardStep lastStep = getLastStep();

        if (lastStep != null && lastStep.onBackPressed()) {
            if (!steps.isEmpty()) {
                return closeLastStep();
            }
        }

        return false;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    public void bringToFront(int position) {
        if (steps.isEmpty() || !steps.isEmpty() && steps.size() - 1 == position && steps.get(position).getStepView() != null) {
            return;
        }

        for (int i = 0; i < position; i++) {
            WizardStep previousStep = steps.get(i);
            if (previousStep.getStepView() != null) {
                removeViewFromParent(previousStep.getStepView());
            }
        }

        WizardStep previousStep = steps.get(position);
        previousStep.setParentLayout(this);

        View stepView = previousStep.getStepView();
        if (stepView == null) {
            stepView = previousStep.onCreateView(activity);
        }

        removeViewFromParent(stepView);
        containerView.addView(stepView, new LayoutParams(-1, -1));
        checkStepViewBackground(stepView);
    }

    public WizardStep getLastStep() {
        if (steps.isEmpty()) {
            return null;
        }
        return steps.get(steps.size() - 1);
    }

    private void presentStepInternalRemoveOld(boolean removeLast, WizardStep step) {
        if (step == null) return;
        if (removeLast) {
            step.setParentLayout(null);
            steps.remove(step);
        } else {
            if (step.getStepView() != null) {
                removeViewFromParent(step.getStepView());
            }
        }
        containerViewBack.setVisibility(View.INVISIBLE);
    }

    private void attachView(WizardStep step) {
        attachView(step, -1);
    }

    private void attachView(WizardStep step, int position) {
        View stepView = step.getStepView();
        if (stepView == null) {
            stepView = step.onCreateView(activity);
        }

        removeViewFromParent(stepView);
        checkStepViewBackground(stepView);

        if (position == -1) {
            containerView.addView(stepView, new LayoutParams(-1, -1));
        } else {
            containerView.addView(stepView, Math.max(Math.min(position, containerView.getChildCount()), 0), new LayoutParams(-1, -1));
        }
    }

    private void removeStepInternal(WizardStep step, boolean allowFinishStep) {
        if (!steps.contains(step)) return;
        if (allowFinishStep && getLastStep() == step) {
            closeLastStep();
        } else {
            if (getLastStep() == step && steps.size() > 1) {
                closeLastStep();
            } else {
                step.setParentLayout(null);
                steps.remove(step);
            }
        }
    }

    private void closeLastStepInternalRemoveOld(WizardStep step) {
        step.setParentLayout(null);
        steps.remove(step);
        containerViewBack.setVisibility(View.INVISIBLE);
        bringChildToFront(containerView);
    }

    private void removeViewFromParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            try {
                parent.removeViewInLayout(view);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    parent.removeView(view);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void checkStepViewBackground(View view) {
        if (view != null && view.getBackground() == null) {
            view.setBackgroundColor(ThemeUtils.getColor(view, R.attr.colorSurface));
        }
    }
}
