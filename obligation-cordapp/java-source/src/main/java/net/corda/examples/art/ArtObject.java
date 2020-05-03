package net.corda.examples.art;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.Amount;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.crypto.NullKeys;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.serialization.ConstructorForDeserialization;

import java.security.PublicKey;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.corda.core.utilities.EncodingUtils.toBase58String;

@BelongsToContract(ArtObjectContract.class)
public class ArtObject implements LinearState {
    private final Amount<Currency> amount;
    private final AbstractParty lender;
    private final AbstractParty borrower;
    private final Amount<Currency> paid;
    private final UniqueIdentifier linearId;
    private final String url;

    @ConstructorForDeserialization
    public ArtObject(Amount<Currency> amount, AbstractParty lender, AbstractParty borrower, Amount<Currency> paid, UniqueIdentifier linearId, String url) {
        this.amount = amount;
        this.lender = lender;
        this.borrower = borrower;
        this.paid = paid;
        this.linearId = linearId;
        this.url = url;
    }

    public ArtObject(Amount<Currency> amount, AbstractParty lender, AbstractParty borrower, Amount<Currency> paid, String url) {
        this.amount = amount;
        this.lender = lender;
        this.borrower = borrower;
        this.paid = paid;
        this.linearId = new UniqueIdentifier();
        this.url = url;
    }

    public ArtObject(Amount<Currency> amount, AbstractParty lender, AbstractParty borrower, String url) {
        this.amount = amount;
        this.lender = lender;
        this.borrower = borrower;
        this.paid = new Amount<>(0, amount.getToken());
        this.linearId = new UniqueIdentifier();
        this.url = url;
    }

    public Amount<Currency> getAmount() {
        return amount;
    }

    public String getUrl() {
        return url;
    }

    public AbstractParty getLender() {
        return lender;
    }

    public AbstractParty getBorrower() {
        return borrower;
    }

    public Amount<Currency> getPaid() {
        return paid;
    }

    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(lender, borrower);
    }

    public ArtObject pay(Amount<Currency> amountToPay) {
        return new ArtObject(
                this.amount,
                this.lender,
                this.borrower,
                this.paid.plus(amountToPay),
                this.linearId,
                this.url
        );
    }

    public ArtObject withNewLender(AbstractParty newLender) {
        return new ArtObject(this.amount, newLender, this.borrower, this.paid, this.linearId, this.url);
    }

    public ArtObject withoutLender() {
        return new ArtObject(this.amount, NullKeys.INSTANCE.getNULL_PARTY(), this.borrower, this.paid, this.linearId, this.url);
    }

    public List<PublicKey> getParticipantKeys() {
        return getParticipants().stream().map(AbstractParty::getOwningKey).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        String lenderString;
        if (this.lender instanceof Party) {
            lenderString = ((Party) lender).getName().getOrganisation();
        } else {
            PublicKey lenderKey = this.lender.getOwningKey();
            lenderString = toBase58String(lenderKey);
        }

        String borrowerString;
        if (this.borrower instanceof Party) {
            borrowerString = ((Party) borrower).getName().getOrganisation();
        } else {
            PublicKey borrowerKey = this.borrower.getOwningKey();
            borrowerString = toBase58String(borrowerKey);
        }

        return String.format("Art(%s): %s owes %s %s",
                this.linearId, borrowerString, lenderString, this.amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ArtObject)) {
            return false;
        }
        ArtObject other = (ArtObject) obj;
        return amount.equals(other.getAmount())
                && lender.equals(other.getLender())
                && borrower.equals(other.getBorrower())
                && paid.equals(other.getPaid())
                && linearId.equals(other.getLinearId())
                && url.equals(other.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, lender, borrower, paid, linearId, url);
    }
}