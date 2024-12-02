package mod.remaker.activity.projectwizard;

import android.os.Bundle;
import android.view.ViewGroup;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import mod.remaker.activity.projectwizard.model.IWizardLayout;
import mod.remaker.activity.projectwizard.model.WizardStep;

import java.util.ArrayList;

public class ProjectWizardActivity extends BaseAppCompatActivity {
    private static final ArrayList<WizardStep> steps = new ArrayList<>();

    private IWizardLayout mWizardLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWizardLayout = IWizardLayout.newLayout(this);
        mWizardLayout.setSteps(steps);

        setContentView(mWizardLayout.getView(), new ViewGroup.LayoutParams(-1, -1));

        if (mWizardLayout.getSteps().isEmpty()) {
            mWizardLayout.addStep(new BasicWizardStep());
        }
    }

    @Override
    public void onBackPressed() {
        if (!mWizardLayout.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
