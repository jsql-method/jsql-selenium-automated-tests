 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlJunit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
        AddAdminTest.class,
        AddApiTest.class,
        AddNewMemberTest.class,
        CopyToClipboardTest.class,
        EditProfileTest.class,
        ElementsPresentDependingOnRoleTest.class,
        Register.class
})
public class TestSuite {

}
